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

# Boxplot
ggplot(bike.sub, aes(x = season, y = count)) +
  geom_boxplot() +
  stat_boxplot(geom = "errorbar") +
  stat_summary(fun.y = mean, col = "black", geom = "point", size = 3) +
  ggtitle("Boxplots of Bike Rentals by Spring and Summer")

# Histogram
xbar <- tapply(bike.sub$count, bike.sub$season, mean)
s <- tapply(bike.sub$count, bike.sub$season, sd)
bike.sub$normal.density <- ifelse(bike.sub$season == "Sp",
                                  dnorm(bike.sub$count, xbar["Sp"] , s["Sp"]),
                                  dnorm(bike.sub$count, xbar["Su"], s["Su"]))
ggplot(bike.sub, aes(x = count)) +
  geom_histogram(aes(y = ..density..),
                 # bins = sqrt(length(bike.sub$count)),
                 bins = 20,
                 fill = "grey", col = "black") +
  facet_grid(season ~ .) +
  geom_density(col = "red", lwd = 1) +
  geom_line(aes(y = normal.density), col = "blue", lwd = 1) +
  ggtitle("Histograms of Bike Rentals by Spring and Summer")

# QQ Plot (Normal Probability Plot)
bike.sub$intercept <- ifelse(bike.sub$season == "Sp",
                             xbar["Sp"], xbar["Su"])
bike.sub$slope <- ifelse(bike.sub$season == "Sp",
                         s["Sp"], s["Su"])
ggplot(bike.sub, aes(sample = count)) +
  stat_qq() +
  facet_grid(season ~ .) +
  geom_abline(data= bike.sub, aes(intercept = intercept,
                                  slope = slope)) +
  ggtitle("QQ Plots of Bike Rentals by Spring and Summer")

# New log-transformed data subset
bike.sub$count <- log(bike.sub$count)

# Boxplot with log-transformed data
ggplot(bike.sub, aes(x = season, y = count)) +
  geom_boxplot() +
  stat_boxplot(geom = "errorbar") +
  stat_summary(fun.y = mean, col = "black", geom = "point", size = 3) +
  ggtitle("Log-Transformed Boxplots of Bike Rentals by Spring and Summer")

# Histogram with log-transformed data
xbar <- tapply(bike.sub$count, bike.sub$season, mean)
s <- tapply(bike.sub$count, bike.sub$season, sd)
bike.sub$normal.density <- ifelse(bike.sub$season == "Sp",
                                  dnorm(bike.sub$count, xbar["Sp"] , s["Sp"]),
                                  dnorm(bike.sub$count, xbar["Su"], s["Su"]))
ggplot(bike.sub, aes(x = count)) +
  geom_histogram(aes(y = ..density..),
                 # bins = sqrt(length(bike.sub$count)),
                 bins = 20,
                 fill = "grey", col = "black") +
  facet_grid(season ~ .) +
  geom_density(col = "red", lwd = 1) +
  geom_line(aes(y = normal.density), col = "blue", lwd = 1) +
  ggtitle("Log-Transformed Histograms of Bike Rentals by Spring and Summer")

# QQ Plot (Normal Probability Plot) with log-transformed data
bike.sub$intercept <- ifelse(bike.sub$season == "Sp",
                             xbar["Sp"], xbar["Su"])
bike.sub$slope <- ifelse(bike.sub$season == "Sp",
                         s["Sp"], s["Su"])
ggplot(bike.sub, aes(sample = count)) +
  stat_qq() +
  facet_grid(season ~ .) +
  geom_abline(data= bike.sub, aes(intercept = intercept,
                                  slope = slope)) +
  ggtitle("Log-Transformed QQ Plots of Bike Rentals by Spring and Summer")

# t-Test
t.test(bike.sub$count ~ bike.sub$season, mu = 0, conf.level = 0.95,
       paired = FALSE, alternative = "two.sided", var.equal = FALSE)

# Data frame with difference variable
bike.tempSub <- subset(bike, bike$season == "Su")
difdata <- data.frame(normaltest = bike.tempSub$temp - bike.tempSub$atemp,
                      temp = bike.tempSub$temp,
                      atemp = bike.tempSub$atemp)

# Boxplot
ggplot(difdata, aes(x = "", y = normaltest)) +
  geom_boxplot() +
  stat_boxplot(geom = "errorbar") +
  stat_summary(fun.y = mean, col = "black", geom = "point", size = 3) +
  ggtitle("Boxplot of Relationship Between 'Feels Like' Temp to Temp in Summer")

# Histogram
xbar <- mean(difdata$normaltest)
s <- sd(difdata$normaltest)
ggplot(difdata, aes(normaltest)) +
  geom_histogram(aes(y = ..density..),
                 # bins = sqrt(length(bike.sub$count)),
                 bins = 20,
                 fill = "grey", col = "black") +
  geom_density(col = "red", lwd = 1) +
  stat_function(fun=dnorm, args=list(mean=xbar, sd=s), col="blue",
                lwd = 1) +
  ggtitle("Histogram of Relationship Between 'Feels Like' Temp to Temp in Summer") +
  xlab("Difference of Temperatures") +
  ylab("Proportion")

# QQ Plot (Normal Probability Plot)
ggplot(difdata, aes(sample = normaltest)) +
  stat_qq() +
  geom_abline(slope = s, intercept = xbar) +
  ggtitle("QQ Plot of Relationship Between 'Feels Like' Temp to Temp in Summer")

# t-Test
t.test(difdata$atemp, difdata$temp, mu = 3, conf.level = 0.99,
       paired = TRUE, alternative = "greater")
