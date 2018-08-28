# Set working directory as default directory
setwd("~")
# Importing data with header and uses separators, tabs, and tab separators
bike <- read.table("/depot/statclass/data/stat35000/2018fall/bike.txt", header = TRUE, sep = "\t")
View(bike)

# Remove 'tbl_df', 'tbl', and removes missing values
str(bike)
bike <-as.data.frame(bike)
str(bike)
bike_cleaned <-bike[complete.cases(bike),]
View(bike_cleaned)

# Save cleaned data set
write.table(bike_cleaned, file="bike_cleaned", row.names=FALSE, sep="\t")

# Transforms the values of "season" to a full name
bike_cleaned$seasonNew <- as.character(bike_cleaned$season)
bike_cleaned$seasonNew[bike_cleaned$season=="Sp"] <- "Spring"
bike_cleaned$seasonNew[bike_cleaned$season=="Su"] <- "Summer"
bike_cleaned$seasonNew[bike_cleaned$season=="Win"] <- "Winter"
View(bike_cleaned)

# Print the new variable
bike_cleaned[c(1, 500, 1100, 3000), c("time_index", "season", "seasonNew")]

# Creates a new variable which implements a calculation
bike_cleaned$countNew <- bike_cleaned$registered+bike_cleaned$casual
# Print the new variable
bike_cleaned[c(1:6), c("time_index", "count", "countNew")]