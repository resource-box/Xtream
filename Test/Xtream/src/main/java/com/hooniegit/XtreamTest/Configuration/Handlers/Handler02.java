package com.hooniegit.XtreamTest.Configuration.Handlers;


import com.hooniegit.Xtream.Tools.Event;
import com.hooniegit.Xtream.Tools.Handler;
import com.hooniegit.XtreamTest.Configuration.DataClass.Sample;

import lombok.Getter;

@Getter
public class Handler02 extends Handler<Sample> {

	@Override
    protected void process(Event<Sample> event) {
//        System.out.println("Handler 02 Started");
    }

}
