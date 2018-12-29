# Install ggplot and ggplot2 and their dependencies
library(ggplot2)
#install.packages("ggplot2")
# Set working directory as default directory
setwd("~")

# The number of repeats
SRS <- 1000
# The number of columns that are being averaged over
n <- 15

# Set title of distribution
title <- paste("Uniform Distribution over the Interval (0, 6): averaged over ", n)

# Calculate the average data, mean, and standard deviation
data.vec <- runif(SRS*n, min=0, m=6) #creates random normal data
data.mat <- matrix(data.vec, nrow = SRS) #separates the data into rows
avg <- apply(data.mat, 1, mean)
xbar <- mean(avg)
s <- sd(avg)

# Print mean and standard deviation
xbar
s

# Generate histogram of these observations
ggplot(data.frame(data=avg), aes(x=avg)) +
  geom_histogram(aes(y=..density..),bins = sqrt(length(avg))+2,
                 fill = "grey",col = "black") +
  geom_density(col = "red", lwd = 1) +
  stat_function(fun=dnorm, args=list(mean=xbar, sd=s), col="blue",
                lwd = 1) +
  ggtitle(title) +
  xlab("Data") +
  ylab("Proportion")

# Generate a normal probability plot of the data
ggplot(data.frame(data=avg), aes(sample=avg)) +
  stat_qq() +
  geom_abline(slope = s, intercept = xbar) +
  ggtitle(title)
