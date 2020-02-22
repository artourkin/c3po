package at.ac.tuwien.ifs.statistics;

import at.ac.tuwien.ifs.model.Source;

public interface SourceStatistics {
    Long getCount();

    Source getSource();
}
