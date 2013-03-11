package org.talwood.marcworx.locparser.containers;

import org.talwood.marcworx.locparser.constants.MarcTransformerSpecs;

public class MarcTransformerStats {
   
    private int workingG0Set;
    private int workingG1Set;
    private boolean multibyte;
    private int workingOffset;
    
    public MarcTransformerStats() {
        this.workingG0Set = MarcTransformerSpecs.DEFAULT_G0_SET;
        this.workingG1Set = MarcTransformerSpecs.DEFAULT_G1_SET;
        this.multibyte = false;
        this.workingOffset = 0;
    }
    
    public MarcTransformerStats(int default_g0_set, int default_g1_set, boolean multibyte, int startingOffset) {
        this.workingG0Set = default_g0_set;
        this.workingG1Set = default_g1_set;
        this.multibyte = multibyte;
        this.workingOffset = startingOffset;
    }

    public int getWorkingG0Set() {
        return workingG0Set;
    }

    public void setWorkingG0Set(int workingG0Set) {
        this.workingG0Set = workingG0Set;
    }

    public int getWorkingG1Set() {
        return workingG1Set;
    }

    public void setWorkingG1Set(int workingG1Set) {
        this.workingG1Set = workingG1Set;
    }

    public boolean isMultibyte() {
        return multibyte;
    }

    public void setMultibyte(boolean multibyte) {
        this.multibyte = multibyte;
    }

    public int getWorkingOffset() {
        return workingOffset;
    }

    public void setWorkingOffset(int workingOffset) {
        this.workingOffset = workingOffset;
    }
    
    public void incrementWorkingOffset(int position) {
        workingOffset += position;
    }
}
