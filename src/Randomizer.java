import java.util.BitSet;
import java.util.Random;

public class Randomizer {

    private Random random;
    private BitSet used;
    private int max;

    public Randomizer(int max, long seed)
    {
        this.max = max;
        this.used = new BitSet(max);
        this.random = new Random(seed);
    }
    public Randomizer(int max)
    {
        this(max, System.currentTimeMillis());
    }
    public int next()
    {
        if (isFinished())
        {
            return -1;
        }
        while (true)
        {
            int r = random.nextInt(max);
            if (!used.get(r))
            {
                used.set(r);
                return r;
            }
        }
    }
    public boolean isFinished()
    {
        return max == used.cardinality();
    }
    public void reset()
    {
        used.clear();
    }
    public int getMax()
    {
        return max;
    }
}