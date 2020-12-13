package com.team2568.frc2020.registers;

import com.team2568.frc2020.registers.ResetableRegister;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.io.*;


import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;



/**
 * Test resetable register class
 * r1 -- register of integer type
 * r2 -- register of dboule type
 * r3 -- register of user defined enumerated type "Day"
 * r4 -- register of user defined class "Payload"
 */

public class TestResetableRegister {

    // random seed
    Random rand; 

    // integer register
    private ResetableRegister<Integer> r1;
    private int r1_reset_val; 

    // double register
    private ResetableRegister <Double> r2;
    private double r2_reset_val; 

    // delta use for double comparison, 4 decimal accuracy
    private final double epsilon=0.0001; 

    // enumerated register
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

        public static Day nextDay() {
            Day[] days = Day.values();
            Random generator = new Random();
            return days[generator.nextInt(days.length)];
        }
    }
    private ResetableRegister <Day> r3;
    private Day r3_reset_val;

    // user defined class
    public class Payload {
        Day data1;
        int data2;
        double data3;

        // create payload with random data
        public Payload (Random generator) {
            Day[] days = Day.values();

            this.data1 = days[generator.nextInt(days.length)];
            this.data2 = generator.nextInt();
            this.data3 = generator.nextDouble();
        }

        public void _debug () {
            System.out.printf("     ==> data1 value=%s\n" , data1); 
            System.out.printf("     ==> data2 value=%d\n" , data2); 
            System.out.printf("     ==> data3 value=%f\n" , data3); 
        }

        // return true if data is equal to val
        public boolean equal(Payload val) {
            assertEquals(this.data1,val.data1);
            assertEquals(this.data2,val.data2);
            assertEquals(this.data3,val.data3,epsilon);
            return true;
        }
    }
    private ResetableRegister <Payload> r4;
    private Payload r4_reset_val;


    // yaml test
    private File file;
    private ObjectMapper objectMapper;
    private ApplicationConfig config;


    // mimick advance "n" nuber of system clock ticks 
    private void _sysClkTick(int n) {
        for (int i = 0 ; i < n; i++) {
            r1.update();
            r2.update();
            r3.update();
            r4.update();
        }
    }

    // mimick system reset 
    private void _sysReset() {
        r1.reset();
        r2.reset();
        r3.reset();
        r4.reset();

        _sysClkTick(1);
    }

    // debug dump register value
    private void _debug() {
        System.out.printf("------------------\n"); 
        System.out.printf("r1 reset value=%d\n" , r1_reset_val); 
        System.out.printf("r2 reset value=%f\n" , r2_reset_val); 
        System.out.printf("r3 reset value=%s\n" , r3_reset_val); 
        System.out.printf("r4 reset value==>  \n"); 
        r4_reset_val._debug();

        System.out.printf("r1 get value=%d\n" , r1.get()); 
        System.out.printf("r2 get value=%f\n" , r2.get()); 
        System.out.printf("r3 get value=%s\n" , r3.get()); 
        System.out.printf("r4 get value==> \n"); 
        r4.get()._debug();
    }



    @Before
    public void setUp() throws Exception
    {
        // random seed
        rand = new Random(); 

        r1_reset_val = rand.nextInt();
        r1 = new ResetableRegister<Integer>(r1_reset_val);

        r2_reset_val = rand.nextDouble();
        r2 = new ResetableRegister<Double>(r2_reset_val);

        r3_reset_val = Day.nextDay();
        r3 = new ResetableRegister<Day>(r3_reset_val);

        r4_reset_val = new Payload(rand);
        r4 = new ResetableRegister<Payload>(r4_reset_val);

        file = new File("src/main/resources/application.yaml");
        objectMapper = new ObjectMapper(new YAMLFactory());
        config = objectMapper.readValue(file, ApplicationConfig.class);

        _sysClkTick(1);

    }


    /**
     * Test yaml
     */
    @Test
    public void testYAML ()
    {

        System.out.println("Application config info " + config.toString());

    }


    /**
     * Test reset
     * @result Reset the system then compare it's value with initial reset
     *          value
     */
    @Test
    public void testReset()
    {

        _sysReset();

        //_debug();

        assertEquals(r1.get().intValue(),r1_reset_val);

        // need epsilon when comparing double value
        assertEquals(r2.get().doubleValue(),r2_reset_val,epsilon);

        assertEquals(r3.get(),r3_reset_val);

        assertTrue(r4.get().equal(r4_reset_val));
    }


    /**
     * Test read-write
     * write follow by read of 10 random values.  Skip "x" clocks every read
     * and write (x = random 1--10).
     */
    @Test
    public void testReadWrite()
    {
        int r1_val;
        double r2_val;
        Day r3_val;
        Payload r4_val;

        // reset system first
        _sysReset();

        for (int i =0; i <= 10; i++) {

            r1_val = rand.nextInt();
            r2_val = rand.nextDouble();
            r3_val = Day.nextDay();
            r4_val = new Payload(rand);

            // write
            r1.set((Integer)r1_val);
            r2.set((Double)r2_val);
            r3.set(r3_val);
            r4.set(r4_val);

            // skip x-cycle
            _sysClkTick (rand.nextInt(10)+1); 

            //_debug();

            // read
            assertEquals(r1.get().intValue(),r1_val);
            assertEquals(r2.get().doubleValue(),r2_val,epsilon);
            assertEquals(r3.get(),r3_val);
            assertTrue(r4.get().equal(r4_val));
        }
    }

    // test read-write with random reset
    // write follow by read of 10 random values.  Skip "x" clocks every read
    // and write (x = random 1--10)... and intermittent random reset
    @Test
    public void testReadWriteWithInterReset()
    {
        int r1_val;
        double r2_val;
        Day r3_val;
        Payload r4_val;


        int isReset;

        // reset system first
        _sysReset();

        for (int i =0; i <= 10; i++) {

            r1_val = rand.nextInt();
            r2_val = rand.nextDouble();
            r3_val = Day.nextDay();
            r4_val = new Payload(rand);

            isReset = rand.nextInt(100)%2; // random

            // write
            r1.set((Integer)r1_val);
            r2.set((Double)r2_val);
            r3.set(r3_val);
            r4.set(r4_val);

            // skip x-cycle
            _sysClkTick (rand.nextInt(10)+1); 

            if (isReset==1) _sysReset();

            //_debug();

            // check value
            if (isReset==1) {
                assertEquals(r1.get().intValue(),r1_reset_val);
                assertEquals(r2.get().doubleValue(),r2_reset_val,epsilon);
                assertEquals(r3.get(),r3_reset_val);
                assertTrue(r4.get().equal(r4_reset_val));
            } else {
                assertEquals(r1.get().intValue(),r1_val);
                assertEquals(r2.get().doubleValue(),r2_val,epsilon);
                assertEquals(r3.get(),r3_val);
                assertTrue(r4.get().equal(r4_val));
            }
        }
    }



}

