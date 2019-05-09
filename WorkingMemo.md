# Working memo
## Tasks we will do
1. Translating the doc into Chinese
2. Writing the program to scrape TMall

## Some info already know
**TMall web scrapping**
1. The format of url with comments
```$xslt
## notice that, the $ua part can be deleted when we request the resources
https://rate.tmall.com/list_detail_rate.htm?
itemId=559482060271
&spuId=1045770338&sellerId=2578685019
&order=3
&currentPage=2
&append=0
&content=1
&tagId=
&posi=
&picture=
&groupId=
&ua=098%23E1hvb9vcvcOvUvCkvvvvvjiPRLdvtjD8P2zvsjnEPmPZ6jiEPF5htjlRRFd96jrE9phvHHia88FBzHi4yWd%2Ft1QY7qr4NYGBRphvCvvvvvmCvpvW7D%2B%2B%2Bbfw7Dup1DbNdphvmpvUap9cHspQQu6CvvyvCWvUWLh9eQArvpvEvv3DmmWFUWbIdphvmpvUjv9vvmmuD4wCvvpvvUmmRphvCvvvvvvCvpvVvmvvvhCvkphvC99vvpHzB8yCvv9vvUvl2DugKqyCvm9vvvvvphvvvvvv9krvpv25vvmm86Cv2vvvvU8BphvUDQvv9krvpvQvmphvLvvaBQvj8txrAnmK5ehhfwFZaB46NB3rAnCl5tDtpa2I1WofVzCw0f0DyBvOJ1kHsX7vV16AxYjxAfyp%2B3%2BKaNoxfBeKVztrzjxlHdUPvpvhvv2MMgwCvvpvCvvvdphvmpvUrv93UpChW46Cvvyv2v9UUkU9AeArvpvEvvLZ3TKdWndtdphvmpvUjOwvv2pI146Cvvyv2COnlYw6JHurvpvEvvkP2Lh1nUkARphvCvvvvvv%3D
&needFold=0
&_ksTS=1556095868968_927
&callback=jsonp928

## this url also can request the resource
https://rate.tmall.com/list_detail_rate.htm?itemId=559482060271&spuId=1045770338&sellerId=2578685019&order=3&currentPage=2&append=0&content=1
```
2.Query parameter
```$xslt
itemId=559482060271&spuId=1045770338&sellerId=2578685019&order=3&currentPage=2&append=0&content=1
```
3.No redirect

4.The last line of every rate page contain the maximum of pages number
5.If the request page is bigger than maximum pages, it will return only the last line
```$xslt
jsonp928({"rateDetail":{"rateCount":{"total":1521,"shop":0,"picNum":464,"used":117},"rateDanceInfo":{"storeType":4,"currentMilles":1556099449619,"showChooseTopic":false,"intervalMilles":0},"rateList":[],"searchinfo":"","from":"search","paginator":{"lastPage":75,"page":75,"items":1498},"tags":[]}})
```
6.貌似我没怎么用都触发了保护机制（提示登录，滑块验证）似乎要登录之后才会好一些（cookie 的保存与使用）
7. intialCommitiesList() throw IOException 
8. 怎么设计多个一类的commodities 之间的关联 class: CommoditiesList???