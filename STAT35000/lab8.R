# Install ggplot and ggplot2 and their dependencies
library(ggplot2)
# install.packages("ggplot2")
# Set working directory as default directory
setwd("~")
# Importing data with header and uses separators, tabs, and tab separators
bike <- read.table("~/bike_cleaned", header = TRUE, sep = "\t")
View(bike)

# Side by side boxplot of data
ggplot(data = bike, aes(x = season, y = humidity)) +
  geom_boxplot() +
  stat_boxplot(geom = "errorbar") +
  stat_summary(fun.y = mean, col = "black", geom = "point", size = 3) +
  ggtitle("Side by Side Boxplots of Relative Humidity Across the Different Seasons")

# Effects plot of data
ggplot(data = bike, aes(x = season, y = humidity)) +
  stat_summary(fun.y = mean, geom = "point") +
  stat_summary(fun.y = mean, geom = "line", aes(group = 1)) +
  ggtitle("Effects Plot of Relative Humidity Across the Different Seasons")

# Table containing the sample size, mean, and standard deviation for each season
tapply(bike$humidity, bike$season, length)
tapply(bike$humidity, bike$season, mean)
tapply(bike$humidity, bike$season, sd)

# Histogram: theoretical density curve
xbar <- tapply(bike$humidity, bike$season, mean)
s <- tapply(bike$humidity, bike$season, sd)
bike$normal.density <- apply(bike, 1, function(x){
  dnorm(as.numeric(x["humidity"]),
        xbar[x["season"]], s[x["season"]])})

# Histogram: Visualization
ggplot(bike, aes(x = humidity)) +
  geom_histogram(aes(y = ..density..), 
                 # bins = sqrt(nrow(bike)) + 2,
                 bins = 20,
                 fill = "grey", col = "black") +
  facet_grid(season ~ .) +
  geom_density(col = "red", lwd = 1) +
  geom_line(aes(y = normal.density), col = "blue", lwd = 1) +
  ggtitle("Histograms of Relative Humidity Across the Different Seasons")

# QQ Plot: slope and intercept
bike$intercept <- apply(bike, 1, function(x){xbar[x["season"]]})
bike$slope <- apply(bike, 1, function(x){s[x["season"]]})

# QQ Plot: Visualization
ggplot(bike, aes(sample = humidity)) +
  stat_qq() +
  facet_grid(season ~ .) +
  geom_abline(data = bike, aes(intercept = intercept, slope = slope)) +
  ggtitle("QQ Plots of Relative Humidity Across the Different Seasons")

# ANOVA significance test
fit <- aov(humidity ~ season, data = bike)
summary(fit)
TukeyHSD(fit, conf.level = 0.99)

# Install multcomp and their dependencies
install.packages("multcomp")
library(multcomp)

# Dunnett multiple-comparison test
Dunnett <- glht(fit, linfct=mcp(season="Dunnett"))
summary(Dunnett)
confint(Dunnett, level = 0.99) 
