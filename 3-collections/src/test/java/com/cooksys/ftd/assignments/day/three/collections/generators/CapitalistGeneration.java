package com.cooksys.ftd.assignments.day.three.collections.generators;

import com.cooksys.ftd.assignments.day.three.collections.model.Capitalist;
import com.cooksys.ftd.assignments.day.three.collections.model.FatCat;
import com.cooksys.ftd.assignments.day.three.collections.model.WageSlave;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import java.util.HashSet;
import java.util.Set;

public interface CapitalistGeneration {

    default Capitalist generateCapitalist(SourceOfRandomness random) {
        return generateCapitalist(random, 0);
    }

    default Capitalist generateCapitalist(SourceOfRandomness random, int depth) {
        return generateCapitalist(random, depth, null);
    }

    default Capitalist generateCapitalist(SourceOfRandomness random, int depth, FatCat root) {
        return generateCapitalist(random, generateFatCat(random, depth, root));
    }

    default Capitalist generateCapitalist(SourceOfRandomness random, FatCat parent) {
        if (random.nextBoolean()) {
            return generateFatCat(random, parent);
        } else {
            return generateWageSlave(random, parent);
        }
    }

    default Set<Capitalist> generateCapitalists(SourceOfRandomness random, int count) {
        return generateCapitalists(random, count, 0);
    }

    default Set<Capitalist> generateCapitalists(SourceOfRandomness random, int count, int depth) {
        return generateCapitalists(random, count, depth, (FatCat) null);
    }

    default Set<Capitalist> generateCapitalists(SourceOfRandomness random, int count, int depth, FatCat root) {
        Set<FatCat> roots = new HashSet<>();
        roots.add(root);
        return generateCapitalists(random, count, roots);
    }

    default Set<Capitalist> generateCapitalists(SourceOfRandomness random, int count, int depth, Set<FatCat> roots) {
        Set<FatCat> parents = new HashSet<>();
        for (FatCat root : roots) {
            parents.add(generateFatCat(random, depth, root));
        }
        return generateCapitalists(random, count, parents);
    }

    default Set<Capitalist> generateCapitalists(SourceOfRandomness random, int count, FatCat parent) {
        Set<FatCat> parents = new HashSet<>();
        parents.add(parent);
        return generateCapitalists(random, count, parents);
    }

    default Set<Capitalist> generateCapitalists(SourceOfRandomness random, int count, Set<FatCat> parents) {
        Set<Capitalist> result = new HashSet<>();
        for (int i = 0; i < count; i++) {
            result.add(generateCapitalist(random, random.choose(parents)));
        }
        return result;
    }

    default FatCat generateFatCat(SourceOfRandomness random) {
        return generateFatCat(random, 0);
    }

    default FatCat generateFatCat(SourceOfRandomness random, int depth) {
        return generateFatCat(random, depth, null);
    }

    default FatCat generateFatCat(SourceOfRandomness random, int depth, FatCat root) {
        return generateFatCat(random, depth > 0 ? generateFatCat(random, depth - 1, root) : root);
    }

    default FatCat generateFatCat(SourceOfRandomness random, FatCat parent) {
        String name = String.format("slave-%s", random.nextInt());
        int salary = random.nextInt((int) 1e2, (int) 1e4);
        return parent != null ? new FatCat(name, salary, parent) : new FatCat(name, salary);
    }

    default Set<FatCat> generateFatCats(SourceOfRandomness random, int count) {
        return generateFatCats(random, count, 0);
    }

    default Set<FatCat> generateFatCats(SourceOfRandomness random, int count, int depth) {
        return generateFatCats(random, count, depth, (FatCat) null);
    }

    default Set<FatCat> generateFatCats(SourceOfRandomness random, int count, int depth, FatCat root) {
        Set<FatCat> roots = new HashSet<>();
        roots.add(root);
        return generateFatCats(random, count, depth, roots);
    }

    default Set<FatCat> generateFatCats(SourceOfRandomness random, int count, int depth, Set<FatCat> roots) {
        Set<FatCat> parents = new HashSet<>();
        for (FatCat root : roots) {
            parents.add(generateFatCat(random, depth, root));
        }
        return generateFatCats(random, count, parents);
    }

    default Set<FatCat> generateFatCats(SourceOfRandomness random, int count, FatCat parent) {
        Set<FatCat> parents = new HashSet<>();
        parents.add(parent);
        return generateFatCats(random, count, parents);
    }

    default Set<FatCat> generateFatCats(SourceOfRandomness random, int count, Set<FatCat> parents) {
        Set<FatCat> result = new HashSet<>();
        for (int i = 0; i < count; i++) {
            result.add(generateFatCat(random, random.choose(parents)));
        }
        return result;
    }

    default WageSlave generateWageSlave(SourceOfRandomness random) {
        return generateWageSlave(random, 0);
    }

    default WageSlave generateWageSlave(SourceOfRandomness random, int depth) {
        return generateWageSlave(random, depth, null);
    }

    default WageSlave generateWageSlave(SourceOfRandomness random, int depth, FatCat root) {
        return generateWageSlave(random, depth > 0 ? generateFatCat(random, depth - 1, root) : root);
    }

    default WageSlave generateWageSlave(SourceOfRandomness random, FatCat parent) {
        String name = String.format("slave-%s", random.nextInt());
        int salary = random.nextInt((int) 1e2, (int) 1e4);
        return parent != null ? new WageSlave(name, salary, parent) : new WageSlave(name, salary);
    }

    default Set<WageSlave> generateWageSlaves(SourceOfRandomness random, int count) {
        return generateWageSlaves(random, count, 0);
    }

    default Set<WageSlave> generateWageSlaves(SourceOfRandomness random, int count, int depth) {
        return generateWageSlaves(random, count, depth, (FatCat) null);
    }

    default Set<WageSlave> generateWageSlaves(SourceOfRandomness random, int count, int depth, FatCat root) {
        Set<FatCat> roots = new HashSet<>();
        roots.add(root);
        return generateWageSlaves(random, count, depth, roots);
    }

    default Set<WageSlave> generateWageSlaves(SourceOfRandomness random, int count, int depth, Set<FatCat> roots) {
        Set<FatCat> parents = new HashSet<>();
        for (FatCat root : roots) {
            parents.add(generateFatCat(random, depth, root));
        }
        return generateWageSlaves(random, count, parents);
    }

    default Set<WageSlave> generateWageSlaves(SourceOfRandomness random, int count, FatCat parent) {
        Set<FatCat> parents = new HashSet<>();
        parents.add(parent);
        return generateWageSlaves(random, count, parents);
    }

    default Set<WageSlave> generateWageSlaves(SourceOfRandomness random, int count, Set<FatCat> parents) {
        Set<WageSlave> result = new HashSet<>();
        for (int i = 0; i < count; i++) {
            result.add(generateWageSlave(random, random.choose(parents)));
        }
        return result;
    }
}
