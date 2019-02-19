# Set working directory
setwd('~/Desktop/Purdue-Courses/CS37300/hw1')

" 3 Data import and summarization "
# Read data into RStudio
data <- read.table("./yelp.csv", header=TRUE, sep=",", quote="\"", comment.char="")
# Summary of data
summary(data)
# Name of columns in the table
names(data)

" 4 1D plots "
# Histogram of 'reviewCount' attribute
hist(data$reviewCount, main='4.A.a) Histogram of Review Count', xlab='Review Count')
# Histogram of logged values for 'reviewCount'
hist(log(data$reviewCount), main='4.A.b) Histogram of Logged Review Count', xlab='Logged Review Count')
# Density plot of logged values for 'reviewCount'
plot(density(log(data$reviewCount)), main='4.A.c) Density Plot of Logged Review Count', xlab='Logged Review Count')
# Barplot of 'state' attribute
barplot(table(data$state), names.arg=names(summary(data$state)), main='4.B) Barplot of State', xlab='States')

" 5 Sampling and transforming data "
# Two new boolean features for pizza and breakfast
servesPizza <- grepl('Pizza', data$categories)
goodForBreakfast <-grepl('breakfast', data$recommendedFor)
summary(servesPizza)
summary(goodForBreakfast)
data <- cbind(data, servesPizza, goodForBreakfast)
# Quantiles for the ‘checkins’ attribute
quantiles <- quantile(data$checkins)
summary(quantiles)
# Subset of the data with ‘checkins’ value ≤ the 1st quartile
checkin_subset <- subset(data, data$checkins <= quantiles[[2]])
summary(quantile(checkin_subset$checkins))
# Summary of subset
summary(checkin_subset[c("checkins", "stars", "noiseLevel", "priceRange", "reviewCount", "goodForGroups")])

" 6 2D plots and correlations "
# Scatterplot Matrix for 5 attributes
pairs(data[c("stars", "reviewCount", "checkins", "longitude", "latitude")], main='6.A Scatterplot Matrix')
# Pairwise correlation among 5 attributes
cor(data[c("stars", "reviewCount", "checkins", "longitude", "latitude")])
# Boxplot of 'attire' vs four attributes
boxplot(checkins ~ attire, data, main='6.C) Boxplot of Checkins vs. Attire', xlab='Attire', ylab='Checkins')
boxplot(reviewCount ~ attire, data, main='6.C) Boxplot of Review Count vs. Attire', xlab='Attire', ylab='Review Count')
boxplot(stars ~ attire, data, main='6.C) Boxplot of Stars vs. Attire', xlab='Attire', ylab='Stars')
boxplot(latitude ~ attire, data, main='6.C) Boxplot of Latitude vs. Attire', xlab='Attire', ylab='Latitude')
# IQR of attribute with most association with ‘attire’
quantile(subset(data, attire == "casual")$reviewCount)
quantile(subset(data, attire == "dressy")$reviewCount)
quantile(subset(data, attire == "formal")$reviewCount)
quantile(subset(data, attire == "")$reviewCount)

" 7 Identifying potential hypotheses "
# Plot illustrating observed
boxplot(reviewCount ~ goodForKids, data, main='7.a) Boxplot of reviewCount vs. goodForKids', xlab='goodForKids', ylab='Review Count', ylim=c(0, 2000))
boxplot(reviewCount ~ smoking, data, main='7.a) Boxplot of reviewCount vs. smoking', xlab='smoking', ylab='Review Count', ylim=c(0, 500))
