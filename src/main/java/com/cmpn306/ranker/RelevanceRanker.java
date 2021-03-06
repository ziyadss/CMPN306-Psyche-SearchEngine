package com.cmpn306.ranker;

import java.util.*;

public class RelevanceRanker {

    public RelevanceRanker() {

    }

    public void rank(HashMap<String, List<QueryPageResult>> resultsMap) {

        HashMap<String, QueryPageResult> docs = new HashMap<>();
        for (Map.Entry<String, List<QueryPageResult>> pair: resultsMap.entrySet()) {
            List<QueryPageResult>     docList     = pair.getValue();
            int                       refDocCount = docList.size();
            Iterator<QueryPageResult> itList      = docList.iterator();
            while (itList.hasNext()) {
                QueryPageResult page = itList.next();
                page.calculateRelevance(refDocCount);
                if (!docs.containsKey(page.getDocUrl())) {
                    docs.put(page.getDocUrl(), page);
                }
                else {
                    double relScoreAdd = docs.get(page.getDocUrl()).getRelevanceScore();
                    docs.get(page.getDocUrl()).setRelevanceScore(relScoreAdd + page.getRelevanceScore());
                    itList.remove();
                }
            }
        }
        sortByRelevance(resultsMap);
    }

    public void sortByRelevance(HashMap<String, List<QueryPageResult>> resultsMap) {
        for (Map.Entry<String, List<QueryPageResult>> pair: resultsMap.entrySet()) {
            List<QueryPageResult> docList = pair.getValue();
            docList.sort((o1, o2) -> Double.compare(o2.getRelevanceScore(), o1.getRelevanceScore()));
        }
    }
}