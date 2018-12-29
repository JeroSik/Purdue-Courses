# Install ggplot and ggplot2 and their dependencies
library(ggplot2)
# install.packages("ggplot2")
# Set working directory as default directory
setwd("~")
# Importing data with header and uses separators, tabs, and tab separators
bike <- read.table("~/bike_cleaned", header = TRUE, sep = "\t")
View(bike)

# New data subset
bike.sub <- subset(bike, bike$season == "Sp" | bike$season == "Su")

# Scatterplot of the data
ggplot(bike.sub, aes(x=temp, y=atemp))+
  geom_point(shape = 1) +
  geom_smooth(method = lm, se = FALSE) +
  ggtitle("Relationship between Temperature and Feels like Temperature") +
  xlab("Temperature") +
  ylab("Feels like Temperature")

# Correlation
cor(bike.sub$temp, bike.sub$atemp)

# Calculate linear regression and get results
bike.lm <- lm(atemp ~ temp, data = bike.sub)
summary(bike.lm)

# Residual Plots
bike.sub$resids = bike.lm$residuals
ggplot(data = bike.sub, aes(x=temp, y=resids))+
  geom_point(shape = 1) +
  geom_smooth(method = lm, se = FALSE) +
  ggtitle("Residual Plot") +
  xlab("Temperature") +
  ylab("Residuals")

# Histogram of the residuals
xbar <- mean(bike.sub$resids)
s <- sd(bike.sub$resids)
ggplot(bike.sub, aes(resids)) +
  geom_histogram(aes(y=..density..),
                 bins = 20,
                 fill = "grey", col = "black") +
  geom_density(col = "red", lwd = 1) +
  stat_function(fun = dnorm, args = list(mean = xbar, sd = s),
                col = "blue", lwd = 1) +
  ggtitle("Histogram of Residuals") +
  xlab("Residuals")

# QQ Plot of the residuals
ggplot(bike.sub, aes(sample = resids)) +
  stat_qq() +
  geom_abline(slope = s, intercept = xbar) +
  ggtitle("QQ Plot of Residuals")

# 99% confidence interval
confint(bike.lm, level = 0.99)

# Temperature at 30
newTemp <- data.frame(temp = 30)

# 99% Intervals
predict(bike.lm, newTemp, interval = "confidence", level = 0.99)
predict(bike.lm, newTemp, interval = "prediction", level = 0.99)
