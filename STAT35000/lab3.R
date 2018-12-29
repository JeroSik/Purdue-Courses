# Install ggplot and ggplot2 and their dependencies
library(ggplot2)
install.packages("ggplot2")
# Set working directory as default directory
setwd("~")

# Generate 10 observations from a normal distribution with mean 10 and standard deviation 5
RandomData <- rnorm(10, mean = 10, sd = 5)
RandomData
title <- "Random Data 10 Observations"

# Generate histogram for the 10 observations
xbar <- mean(RandomData)
s <- sd(RandomData)
ggplot(data.frame(RandomData=RandomData), aes(x=RandomData)) +
  geom_histogram(aes(y=..density..),bins = sqrt(length(RandomData))+2,
                 fill = "grey",col = "black") +
  geom_density(col = "red", lwd = 1) +
  stat_function(fun=dnorm, args=list(mean=xbar, sd=s), col="blue",
                lwd = 1) +
  ggtitle(title) +
  xlab("Data") +
  ylab("Proportion")

# Generate a normal probability plot for the 10 observations
ggplot(data.frame(RandomData=RandomData), aes(sample=RandomData)) +
  stat_qq() +
  geom_abline(slope = s, intercept = xbar) +
  ggtitle(title)

# Generate 100 observations from a normal distribution with mean 10 and standard deviation 5
RandomData <- rnorm(100, mean = 10, sd = 5)
RandomData
title <- "Random Data 100 Observations"

# Generate histogram of these observations
xbar <- mean(RandomData)
s <- sd(RandomData)
ggplot(data.frame(RandomData=RandomData), aes(x=RandomData)) +
  geom_histogram(aes(y=..density..),bins = sqrt(length(RandomData))+2,
                 fill = "grey",col = "black") +
  geom_density(col = "red", lwd = 1) +
  stat_function(fun=dnorm, args=list(mean=xbar, sd=s), col="blue",
                lwd = 1) +
  ggtitle(title) +
  xlab("Data") +
  ylab("Proportion")

# Generate a normal probability plot of the data
ggplot(data.frame(RandomData=RandomData), aes(sample=RandomData)) +
  stat_qq() +
  geom_abline(slope = s, intercept = xbar) +
  ggtitle(title)

# Generate 100 observations
n <- 100

# right skewed: exponential distribution (lambda=4)
right <- rexp(n,rate=4)
RandomData <- right
title <- "Right Skewed Distribution"

# Generate histogram of these observations
xbar <- mean(RandomData)
s <- sd(RandomData)
ggplot(data.frame(RandomData=RandomData), aes(x=RandomData)) +
  geom_histogram(aes(y=..density..),bins = sqrt(length(RandomData))+2,
                 fill = "grey",col = "black") +
  geom_density(col = "red", lwd = 1) +
  stat_function(fun=dnorm, args=list(mean=xbar, sd=s), col="blue",
                lwd = 1) +
  ggtitle(title) +
  xlab("Data") +
  ylab("Proportion")

# Generate a normal probability plot of the data
ggplot(data.frame(RandomData=RandomData), aes(sample=RandomData)) +
  stat_qq() +
  geom_abline(slope = s, intercept = xbar) +
  ggtitle(title)

# left skewed: Beta distribution (on [0,1], alpha = 6, beta = 1)
left <- rbeta(n,6,1)
RandomData <- left
title <- "Left Skewed Distribution"

# Generate histogram of these observations
xbar <- mean(RandomData)
s <- sd(RandomData)
ggplot(data.frame(RandomData=RandomData), aes(x=RandomData)) +
  geom_histogram(aes(y=..density..),bins = sqrt(length(RandomData))+2,
                 fill = "grey",col = "black") +
  geom_density(col = "red", lwd = 1) +
  stat_function(fun=dnorm, args=list(mean=xbar, sd=s), col="blue",
                lwd = 1) +
  ggtitle(title) +
  xlab("Data") +
  ylab("Proportion")

# Generate a normal probability plot of the data
ggplot(data.frame(RandomData=RandomData), aes(sample=RandomData)) +
  stat_qq() +
  geom_abline(slope = s, intercept = xbar) +
  ggtitle(title)

# long tailed: t-distribution (df = 1)
long <- rt(n,df=1)
RandomData <- long
title <- "Long Tailed Distribution"

# Generate histogram of these observations
xbar <- mean(RandomData)
s <- sd(RandomData)
ggplot(data.frame(RandomData=RandomData), aes(x=RandomData)) +
  geom_histogram(aes(y=..density..),bins = sqrt(length(RandomData))+2,
                 fill = "grey",col = "black") +
  geom_density(col = "red", lwd = 1) +
  stat_function(fun=dnorm, args=list(mean=xbar, sd=s), col="blue",
                lwd = 1) +
  ggtitle(title) +
  xlab("Data") +
  ylab("Proportion")

# Generate a normal probability plot of the data
ggplot(data.frame(RandomData=RandomData), aes(sample=RandomData)) +
  stat_qq() +
  geom_abline(slope = s, intercept = xbar) +
  ggtitle(title)

# short tailed: Uniform (on [-2,2]);
short <- runif(n,min=-2,max=2)
RandomData <- short
title <- "Short Tailed Distribution"

# Generate histogram of these observations
xbar <- mean(RandomData)
s <- sd(RandomData)
ggplot(data.frame(RandomData=RandomData), aes(x=RandomData)) +
  geom_histogram(aes(y=..density..),bins = sqrt(length(RandomData))+2,
                 fill = "grey",col = "black") +
  geom_density(col = "red", lwd = 1) +
  stat_function(fun=dnorm, args=list(mean=xbar, sd=s), col="blue",
                lwd = 1) +
  ggtitle(title) +
  xlab("Data") +
  ylab("Proportion")

# Generate a normal probability plot of the data
ggplot(data.frame(RandomData=RandomData), aes(sample=RandomData)) +
  stat_qq() +
  geom_abline(slope = s, intercept = xbar) +
  ggtitle(title)

# Importing data with header and uses separators, tabs, and tab separators
bike <- read.table("~/bike_cleaned", header = TRUE, sep = "\t")
View(bike)
title <- "Registered Rentals Distribution"

# Generate histogram of these observations
xbar <- mean(bike$registered)
s <- sd(bike$registered)
ggplot(bike, aes(registered)) +
  geom_histogram(aes(y =..density..), bins = 20, fill="grey", col="black") + 
  geom_density(col="red", lwd=1) +
  stat_function(fun=dnorm, args = list(mean = xbar, sd = s), col = "blue",
                lwd = 1) +
  ggtitle(title) +
  xlab("Data") +
  ylab("Proportion")

# Generate a normal probability plot of the data
ggplot(bike, aes(sample=registered)) +
  stat_qq() +
  geom_abline(slope = s, intercept = xbar) +
  ggtitle(title)
