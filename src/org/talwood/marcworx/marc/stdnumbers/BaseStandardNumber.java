package org.talwood.marcworx.marc.stdnumbers;

import java.io.Serializable;

public interface BaseStandardNumber extends Serializable {
    public boolean isValid();
    public String getTagName();
    public int getTagNumber();
    public String getSortable();
    public String getSortableExtended();
    public String getRaw();
    public String getDisplayable();
    public StandardNumberDefinitions getErrNo();
    @Override
    public boolean equals(Object o);
}
