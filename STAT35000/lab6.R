# Install ggplot and ggplot2 and their dependencies
library(ggplot2)
# install.packages("ggplot2")
# Set working directory as default directory
setwd("~")
# Importing data with header and uses separators, tabs, and tab separators
bike <- read.table("~/bike_cleaned", header = TRUE, sep = "\t")
View(bike)

# Histogram
xbar <- mean(bike$humidity)
s <- sd(bike$humidity)
ggplot(bike, aes(humidity)) +
  geom_histogram(aes(y = ..density..),
                 # bins = sqrt(nrow(bike))+2,
                 bins = 20,
                 fill = "grey", col = "black") +
  geom_density(col = "red", lwd = 1) +
  stat_function(fun = dnorm, args = list(mean = xbar, sd = s),
                col="blue", lwd = 1) +
  ggtitle("Histogram of Relative Humidity") +
  xlab("Humidity") +
  ylab("Proportion")
# Boxplot
ggplot(bike, aes(x = "", y = humidity)) +
  stat_boxplot(geom = "errorbar") +
  geom_boxplot() +
  ggtitle("Boxplot of Humidity") +
  stat_summary(fun.y = mean, col = "black", geom = "point", size = 3)
# QQ Plot (Normal Probability Plot)
ggplot(bike, aes(sample = humidity)) +
  stat_qq() +
  geom_abline(slope = s, intercept = xbar) +
  ggtitle("QQ Plot of Humidity")

# Sample Mean
xbar
# Sample Standard Deviation
s
# Standard Error of the Mean (Standard Deviation of the Estimator) 
sem <- s/sqrt(nrow(bike))
sem

# 99% Upper Bound with mu = 60
t.test(bike$humidity, conf.level = 0.99, mu = 60, alternative = "less")

# 99% Confidence Interval with mu = 70
t.test(bike$humidity, conf.level = 0.99, mu = 70, alternative = "two.sided")

# 99% Lower Bound with mu 60 
t.test(bike$humidity, conf.level = 0.99, mu = 60, alternative = "two.sided")
