TAK <- read.csv("~/IdeaProjects/tak-ai/rec/TakAIRecords-0.csv", fileEncoding="UTF-16")
WEIGHTS <- read.csv("~/IdeaProjects/tak-ai/weight-cache/TakAIWeights-0.csv", fileEncoding="UTF-16")

hist(as.numeric(WEIGHTS[nrow(WEIGHTS),]))
scatter.smooth(as.numeric(WEIGHTS[nrow(WEIGHTS),]))

mean(TAK$numTurns[1:1086])
mean(TAK$numTurns[1086:2086])
mean(TAK$numTurns[2086:3086])
