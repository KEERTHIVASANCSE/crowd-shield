package com.crowdshield.config;

import com.crowdshield.model.Alert;
import com.crowdshield.model.DensityData;
import com.crowdshield.model.Route;
import com.crowdshield.repository.AlertRepository;
import com.crowdshield.repository.DensityDataRepository;
import com.crowdshield.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final DensityDataRepository densityDataRepository;
    private final AlertRepository alertRepository;
    private final RouteRepository routeRepository;

    public DataInitializer(DensityDataRepository densityDataRepository,
                           AlertRepository alertRepository,
                           RouteRepository routeRepository) {
        this.densityDataRepository = densityDataRepository;
        this.alertRepository = alertRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public void run(String... args) {
        try {
            seedDensityData();
            seedAlerts();
            seedRoutes();
            log.info("CrowdShield sample data successfully verified and initialized.");
        } catch (Exception e) {
            log.warn("Sample data initialization encountered an issue: {}", e.getMessage());
        }
    }

    private void seedDensityData() {
        if (densityDataRepository.count() == 0) {
            LocalDateTime now = LocalDateTime.now();
            densityDataRepository.save(new DensityData(1, now, 45, "Medium", 19.9975, 73.7898));
            densityDataRepository.save(new DensityData(2, now, 20, "Low", 20.0059, 73.7749));
            densityDataRepository.save(new DensityData(3, now, 8, "Very Low", 19.9923, 73.7749));
            densityDataRepository.save(new DensityData(4, now, 60, "High", 20.0104, 73.7749));
            densityDataRepository.save(new DensityData(1, now.minusHours(1), 35, "Medium", 19.9975, 73.7898));
            densityDataRepository.save(new DensityData(2, now.minusHours(1), 15, "Low", 20.0059, 73.7749));
            densityDataRepository.save(new DensityData(3, now.minusHours(1), 25, "Medium", 19.9923, 73.7749));
            densityDataRepository.save(new DensityData(4, now.minusHours(1), 50, "High", 20.0104, 73.7749));
            log.info("Seeded initial density data records.");
        }
    }

    private void seedAlerts() {
        if (alertRepository.count() == 0) {
            LocalDateTime now = LocalDateTime.now();
            alertRepository.save(new Alert(1, "Congestion", "High crowd density detected in Zone 1", "active", now, 19.9975, 73.7898));
            alertRepository.save(new Alert(4, "Congestion", "Very high crowd density detected in Zone 4", "active", now, 20.0104, 73.7749));
            alertRepository.save(new Alert(2, "Safety", "Safety protocol activated in Zone 2", "resolved", now.minusHours(2), 20.0059, 73.7749));
            alertRepository.save(new Alert(3, "Maintenance", "Maintenance required in Zone 3", "active", now.minusMinutes(30), 19.9923, 73.7749));
            alertRepository.save(new Alert(1, "Emergency", "Emergency situation in Zone 1", "resolved", now.minusHours(3), 19.9975, 73.7898));
            log.info("Seeded initial alerts.");
        }
    }

    private void seedRoutes() {
        if (routeRepository.count() == 0) {
            routeRepository.save(new Route("Gate 1", "Main Hall", "Direct route through corridor A", true));
            routeRepository.save(new Route("Gate 2", "Main Hall", "Alternative route via corridor B", true));
            routeRepository.save(new Route("Main Hall", "Exit 1", "Emergency exit route", false));
            routeRepository.save(new Route("Gate 3", "Main Hall", "Scenic route through garden", true));
            routeRepository.save(new Route("Main Hall", "Exit 2", "Secondary exit route", true));
            routeRepository.save(new Route("Gate 1", "Exit 3", "Quick exit route", false));
            log.info("Seeded initial routes.");
        }
    }
}
