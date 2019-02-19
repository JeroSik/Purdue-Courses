# Set working directory
setwd('~/Desktop/Purdue-Courses/CS37300/R_lecture_guide')

# read in data
d <- read.table(file='iris.dat',sep=',')
d <- read.table(file='iris.dat',sep=',',header=FALSE)

# histogram
hist(d[,2], main='Histogram of Sepal Width', xlab='Sepal Width')
hist(d[,2], main='Histogram of Sepal Width', xlab='Sepal Width', breaks=20)

# density
plot(density(d[,2]),main='Density of Sepal Width')
plot(density(d[,2],adjust=0.5),main='Density of Sepal Width')

# subset
names(summary(d[,5]))
subset(d[d$V5=='Iris-setosa',])
subset(d[d$V5=='Iris-setosa',])[,3]

# barplot
x1 <- mean(d[d$V5=='Iris-setosa',][,3])
x2 <- mean(d[d$V5=='Iris-versicolor',][,3])
x3 <- mean(d[d$V5=='Iris-virginica',][,3])
barplot(c(x1,x2,x3),names.arg=names(summary(d[,5])))

# boxplot
boxplot(d$V1 ~ d$V5, d)

# scatterplot
plot(d[,c(1,3)],xlab='sepal length',ylab='petal length')

# locally weighted linear regression
lines(lowess(d[,c(1,3)]))

# transforming data
V1b <- d[,1]^2
summary(V1b)
d <- cbind(d,V1b)
d

