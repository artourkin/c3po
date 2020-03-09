package at.ac.tuwien.ifs.model.statistics;

import at.ac.tuwien.ifs.model.Source;

public class SourceStatistic {
    private Long count;
    private Source source;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
