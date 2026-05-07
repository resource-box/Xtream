package com.hooniegit.XtreamTest.Tester;

import com.hooniegit.Xtream.Tools.StreamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hooniegit.XtreamTest.Configuration.DataClass.Sample;
import jakarta.annotation.PostConstruct;

import java.util.concurrent.atomic.AtomicInteger;


@Service
public class XtreamTester {

    private final StreamManager<Sample> manager;
    private final AtomicInteger count = new AtomicInteger(0);

    @Autowired
    public XtreamTester(StreamManager<Sample> manager) {
        this.manager = manager;
    }

    @PostConstruct
    private void demo() {

        for (int cnt=0; cnt <= 1000; cnt++) {
            long start = System.nanoTime();

            for (int i=0; i<=400000; i++) {

                this.manager.getNextStream().publishInitialEvent(new Sample("Hello, World!"));

            }

            long end = System.nanoTime();

            long spent = end - start;
            long spentMillis = spent / 1_000_000;
            long spentSeconds = spentMillis / 1000;

            System.out.println(">> " + spent + "[ns] : " + spentMillis + "[ms] : " + spentSeconds + "[s]");
        }
    }

}
