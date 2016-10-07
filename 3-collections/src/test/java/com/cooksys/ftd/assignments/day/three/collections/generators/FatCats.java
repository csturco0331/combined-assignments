package com.cooksys.ftd.assignments.day.three.collections.generators;

import com.cooksys.ftd.assignments.day.three.collections.model.FatCat;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class FatCats extends Generator<FatCat> implements CapitalistGeneration {
    private Cap cap;
    private Cat cat;

    public FatCats() {
        super(FatCat.class);
    }

    public FatCat generate(SourceOfRandomness random, GenerationStatus status) {
        int depth = cap != null ? cap.depth() : cat != null ? cat.depth() : -1;
        return generateFatCat(random, depth >= 0 ? depth : status.size());
    }

    public void configure(Cap cap) {
        this.cap = cap;
    }

    public void configure(Cat cat) {
        this.cat = cat;
    }
}
