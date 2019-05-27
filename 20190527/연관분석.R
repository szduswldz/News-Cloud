setwd("C:/RPrj")
library(N2H4)
cate<-getMainCategory()        
tcate<-cate$sid1[3]                 
subCate<-cbind(sid1=tcate,getSubCategory(sid1=tcate))    
tscate<-subCate$sid2[9]    
strDate<-"20190527"   
library(KoNLP)
useSejongDic()
library(wordcloud)

#뉴스수집
newsData<-c()
    for (sid1 in tcate){
        for (sid2 in tscate){
        pageUrl<-paste0("http://news.naver.com/main/list.nhn?sid2=",sid2,"&sid1=",sid1,"&mid=shm&mode=LS2D&date=",strDate)
          max<-getMaxPageNum(pageUrl)
      
          for (pageNum in 1:max){
            pageUrl<-paste0("http://news.naver.com/main/list.nhn?sid2=",sid2,"&sid1=",sid1,"&mid=shm&mode=LS2D&date=",strDate,"&page=",pageNum)
            newsList<-getUrlListByCategory(pageUrl)
        
            for (newslink in newsList$links){
               tryi<-0
              tem<-try(getContent(newslink), silent = TRUE)
              while(tryi<=5&&class(tem)=="try-error"){
                tem<-try(getContent(newslink), silent = TRUE)
                tryi<-tryi+1
              }
              if(class(tem$datetime)[1]=="POSIXct"){
                   newsData<-rbind(newsData, tem)
                 }
            }
            dir.create("./data",showWarnings=F)
            write.csv(newsData, file=paste0("./data/news",tcate,"_",tscate,"_",strDate,".csv"),row.names = F)
        }
    }
}


library(stringr)
useSejongDic()

#필요없는 단어 삭제
cleaning_text<-function(dat){
    char<-gsub("[[:cntrl:]]","",dat)
    char<-gsub("@[[:graph:]]*","",char)
    char<-gsub("[A-z]","",char)
    char<-gsub("\\▶","",char)
    char<-gsub("무단전재 및 재배포 금지","",char)
    char<-gsub("금지","",char)
    char<-gsub("재배포","",char)
    char<-gsub("년","",char)
    char<-gsub("무단","",char)
    char<-gsub("전재","",char)
    char<-gsub("바로가기","",char)
    char<-gsub("기자","",char)
    char<-gsub("구독","",char)
    char<-gsub("채널 구독하기","",char)
    char<-gsub("연합","",char)
    char<-gsub("[^\uAC00-\uD7A3xfe a-zA-Zㄱ-ㅎㅏ-ㅣ가-R\\s]","",char,perl=FALSE)
    char<-gsub("ⓒ","",char)
}


# 기사본문에 언론사명 삭제
for (i in 1:length(newsData$press)){
    news_body<-gsub(newsData$press[i],"",news_body)
}


# 뉴스 텍스트를 정제하여, TermDocumentMatrix 생성
news_body<-cleaning_text(newsData$body)
news_body<-unlist(news_body)
news_body<-Filter(function(x){nchar(x)>=2},news_body)

library(tm)
cps<-Corpus(VectorSource(news_body))
ko.words <- function(doc){
  d <- as.characters(doc)
  extractNoun(d)
}

tdm<-TermDocumentMatrix(cps,
   control=list( tokenize=ko.words,
      removePunctuation=T,
      stopwords=T,
      removeNumbers=T,
      wordLengths=c(4,Inf),
      weighting=weightTfIdf))

tdm.matrix <- as.matrix(tdm)
word.count <- rowSums(tdm.matrix)
word.order <- order(word.count, decreasing=T)
rownames(tdm.matrix)[word.order[1:20]]
freq.words <- tdm.matrix[word.order[1:20], ]
co.matrix <- freq.words %*% t(freq.words)


# 단어와 단어간의 빈도수에 따라 노드별(단어) 가중치를 부여
library("tidyr")
library("dplyr")


co.dataframe<-as.data.frame(co.matrix)
co.dataframe$child = names(co.dataframe)
co.dataframe<-co.dataframe%>%gather(parent,degree,1:(ncol(co.dataframe)-1))
co.dataframe<-left_join(co.dataframe,co.dataframe%>%group_by(child)%>%summarise(sum=sum(degree)),by="child")
co.dataframe<-co.dataframe[-which(co.dataframe$degree==0),]
co.dataframe$sum <- co.dataframe$sum*30
co.dataframe<-co.dataframe[-which(co.dataframe$child==co.dataframe$parent),]

row.names(co.dataframe) = NULL
duple=vector()
for(i in 1:nrow(co.dataframe)){
for(j in 1:nrow(co.dataframe)){
if(i!=j){
if(co.dataframe$child[i]==co.dataframe$parent[j]&co.dataframe$parent[i]==co.dataframe$child[j]){
duple = append(duple,j)
}}}}

library("ggraph")
library("igraph")


#
png("./data/as_image.png",width=800,height = 600)

p<-co.dataframe%>%
graph_from_data_frame() %>% 
ggraph(layout="fr") +
geom_edge_link(aes(edge_alpha = sum,edge_width=sum)) +
geom_node_point(color="darkslategray4", size=17) +
geom_node_text(aes(label=name),vjust=0) +
theme_void()


plot(p)

