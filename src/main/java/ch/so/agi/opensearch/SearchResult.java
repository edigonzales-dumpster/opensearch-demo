package ch.so.agi.opensearch;

public class SearchResult {
    private String label;
    
    public SearchResult() {}
    
    public SearchResult(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
