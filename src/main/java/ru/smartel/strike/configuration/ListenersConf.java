package ru.smartel.strike.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import ru.smartel.strike.seeder.ConflictReasonSeeder;
import ru.smartel.strike.seeder.ConflictResultSeeder;

@Configuration
public class ListenersConf  {

    @Autowired
    ConflictResultSeeder conflictResultSeeder;
    @Autowired
    ConflictReasonSeeder conflictReasonSeeder;

    @EventListener
    public void seed(ContextRefreshedEvent event) {

        System.out.println("seeding");
        conflictResultSeeder.seed();
        conflictReasonSeeder.seed();
    }
}
