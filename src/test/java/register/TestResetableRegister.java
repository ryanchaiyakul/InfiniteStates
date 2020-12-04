
package register;

import com.team2568.frc2020.registers.ResetableRegister;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class TestResetableRegister {

    // delta use for double comparison, 4 decimal accuracy
    private final double epsilon = 0.0001;

    private ResetableRegister<Integer> r1;
    private int r1_reset_val;

    private ResetableRegister<Double> r2;
    private double r2_reset_val;

    // random seed
    Random rand;

    // mimick advance "n" nuber of system clock ticks
    private void _sysClkTick(int n) {
        for (int i = 0; i < n; i++) {
            r1.update();
            r2.update();
        }
    }

    // mimick system reset
    private void _sysReset() {
        r1.reset();
        r2.reset();

        _sysClkTick(1);
    }

    // debug dump register value
    private void _debug() {
        System.out.printf("------------------\n");
        System.out.printf("r1 reset value=%d\n", r1_reset_val);
        System.out.printf("r2 reset value=%f\n", r2_reset_val);

        // System.out.printf("r1=%d\n" , r1.get().intValue());
        // System.out.printf("r2=%f\n" , r2.get().doubleValue());
        System.out.printf("r1 get value=%d\n", r1.get());
        System.out.printf("r2 get value=%f\n", r2.get());

    }

    @Before
    public void setUp() throws Exception {
        // random seed
        rand = new Random();

        r1_reset_val = rand.nextInt();
        r1 = new ResetableRegister<Integer>(r1_reset_val);

        r2_reset_val = rand.nextDouble();
        r2 = new ResetableRegister<Double>(r2_reset_val);

        _sysClkTick(1);

    }

    /**
     * Test reset
     * 
     * @result Reset the system then compare it's value with initial reset value
     */
    @Test
    public void testReset() {

        _sysReset();

        // _debug();

        assertEquals(r1.get().intValue(), r1_reset_val);
        assertEquals(r2.get().doubleValue(), r2_reset_val, epsilon);
    }

    // test read-write
    // write follow by read of 10 random values. Skip "x" clocks every read
    // and write (x = random 1--10).
    @Test
    public void testReadWrite() {
        int r1_val;
        double r2_val;

        // reset system first
        _sysReset();

        for (int i = 0; i <= 10; i++) {

            r1_val = rand.nextInt();
            r2_val = rand.nextDouble();

            // write
            r1.set((Integer) r1_val);
            r2.set((Double) r2_val);

            // skip x-cycle
            _sysClkTick(rand.nextInt(10) + 1);

            // _debug();

            // read
            assertEquals(r1.get().intValue(), r1_val);
            assertEquals(r2.get().doubleValue(), r2_val, epsilon);
        }
    }

    // test read-write with random reset
    // write follow by read of 10 random values. Skip "x" clocks every read
    // and write (x = random 1--10)... and intermittent random reset
    @Test
    public void testReadWriteWithInterReset() {
        int r1_val;
        double r2_val;
        int isReset;

        // reset system first
        _sysReset();

        for (int i = 0; i <= 10; i++) {

            r1_val = rand.nextInt();
            r2_val = rand.nextDouble();
            isReset = rand.nextInt(100) % 2; // random

            // write
            r1.set((Integer) r1_val);
            r2.set((Double) r2_val);

            // skip x-cycle
            _sysClkTick(rand.nextInt(10) + 1);

            if (isReset == 1)
                _sysReset();

            // _debug();

            // check value
            if (isReset == 1) {
                assertEquals(r1.get().intValue(), r1_reset_val);
                assertEquals(r2.get().doubleValue(), r2_reset_val, epsilon);

            } else {
                assertEquals(r1.get().intValue(), r1_val);
                assertEquals(r2.get().doubleValue(), r2_val, epsilon);
            }
        }
    }

}
