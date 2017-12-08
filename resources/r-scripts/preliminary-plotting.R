TAK <- read.csv("~/IdeaProjects/tak-ai/rec/TakAIRecords-0.csv", fileEncoding="UTF-16")
WEIGHTS <- read.csv("~/IdeaProjects/tak-ai/weight-cache/TakAIWeights-0.csv", fileEncoding="UTF-16")

plot(table(TAK$winner, TAK$p1))
barplot(table(TAK$winner))
scatter.smooth(tail(TAK$score, 4000))
scatter.smooth(tail(TAK$numTurns, 4000))


weights <- subset(WEIGHTS[2:ncol(WEIGHTS)])
hist(as.numeric(weights[nrow(weights),]))
scatter.smooth(as.numeric(weights[nrow(weights),]))

weights_scaled <- as.data.frame(scale(WEIGHTS))
f <- as.formula(paste(colnames(weights_scaled)[1], '~', paste(colnames(weights_scaled)[2:ncol(weights_scaled)], collapse='+')))
regression <- lm(f, weights_scaled)

summary(regression)
