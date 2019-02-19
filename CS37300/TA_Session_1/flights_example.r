# Install datasest
# install.packages(nycflights13)

#Load Dataset library
library(nycflights13)

# Test it
nycflights13::flights
flights

# in R studio: open a view of the dataframe
View(flights)

# Then we can try some centrality measures over one of the columns
flights$arr_delay
mean(flights$arr_delay)
# As we can see, the result is NA because we have some NA values
mean(na.omit(flights$arr_delay))
sd(na.omit(flights$arr_delay))


# To make the plots I will use R standard libraries and ggplot
# ggplot2 helps us do data visualization
# allows us to get a sense for how quantitative variables compare and spread
# provide insights into data -> patterns, outliers
# uncomment the following line to install the package
# install.packages('ggplot2', dep = TRUE)
# load the package 
library(ggplot2)

# dyplr2 helps us with data wrangling
# install.packages("dplyr")
# dplyr library for some data manipulation
# install.packages("Rcpp")
library(dplyr)

# What kind of plot I can use to see if there is some relationship between
# departing late and arriving late?
# For demostration purposes lets just take flights from american airlines
aa_flights = flights[flights$carrier=="AA", ]
View(aa_flights)
glimpse(flights)

# As we can see, arrival_delay and departure_delay are positively correlated
plot(aa_flights$arr_delay, aa_flights$dep_delay)
#Also it is kind of difficult to appreciate how many points we have in an area

# We can give it some color and try increasing the transparency
plot(aa_flights$arr_delay, aa_flights$dep_delay, col = rgb(0,90,0,50,maxColorValue=255), cex=0.1)

# With ggplot
# geom: stands for geometric object -> in this case point
# aes -> aesthetic attributes of geom
ggplot(data = aa_flights, mapping = aes(x = dep_delay, y = arr_delay)) + 
  geom_point(alpha = 0.2, col=rgb(0,0.3,0, 0.2))
# But it is still hard to see. To see it we can try some controur plots 
# geom_density_2d -> 2D kernel density estimation and display results with contours
ggplot(data = aa_flights, mapping = aes(x = dep_delay, y = arr_delay)) + geom_density_2d()

# view another table, the weather table
View(weather)

# Now lets try analyzing the weather when these flights took place
plot(weather[weather$origin == 'EWR',][,-c(1,2,4,5,7,8,9,10,11,13,14,15)])

#It is difficult to see the Trends this way, lets try visualize the temperature as histograms moth by month
ggplot(data = weather, aes(x = temp)) +
  geom_histogram(binwidth = 5, color = "white") +
  facet_wrap(~ month, nrow = 4)

# or we can try a boxplot
# is the data skewed? symmetric?
ggplot(data = weather, mapping = aes(x = factor(month), y = temp)) +
  geom_boxplot()

# Now, lets say we want to compare the carriers and determine which ones
# have the most delay because we want to purchase one with the least
ggplot(data = flights, mapping = aes(x = carrier, y = arr_delay)) +
  geom_boxplot()

#What we can not see in this plot? Actually how many flights they have
# Here we can see a barplot of the number of flights made by every carrier   
ggplot(data = flights, mapping = aes(x = carrier)) +
  geom_bar() 

#(flights["origin"])
#(airports["faa"])

# Stacked bar plot
# use the dpylr pipe operator to chain together functions
flights_namedports <- flights %>% inner_join(airports, by = c("origin" = "faa"))

#pretty but.... useful?
ggplot(data = flights_namedports, mapping = aes(x = carrier, fill = name)) +
  geom_bar()

# faceting allows us to create multiples of the same plot over a categorical variable
# in this case name
ggplot(data = flights_namedports, mapping = aes(x = carrier, fill = name)) +
  geom_bar() +
  facet_grid(name ~ .)

# find flights with the most common destination
# group_by -> dplyr
freq_dest = flights %>% 
  group_by(dest) %>% 
  summarize(num_flights = n())


