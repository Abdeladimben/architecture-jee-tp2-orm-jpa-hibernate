package ma.bdcc;

import ma.bdcc.entities.*;
import ma.bdcc.repositories.*;
import ma.bdcc.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

    @Bean
    CommandLineRunner start(IHospitalService hospitalService,
                            PatientRepository patientRepository,
                            MedecinRepository medecinRepository,
                            RendezVousRepository rendezVousRepository) {
        return args -> {
            Stream.of("Mohamed", "Ahmed", "Fatima", "Sara").forEach(name -> {
                Patient patient = Patient.builder()
                    .nom(name)
                    .dateNaissance(new Date())
                    .malade(Math.random() > 0.5)
                    .score((int) (Math.random() * 100))
                    .build();
                hospitalService.savePatient(patient);
            });

            Stream.of("Dr. Alaoui", "Dr. Benali", "Dr. Chafik").forEach(name -> {
                Medecin medecin = Medecin.builder()
                    .nom(name)
                    .email(name.toLowerCase().replace(" ", ".") + "@hospital.ma")
                    .specialite(Math.random() > 0.5 ? "Cardiologue" : "Dentiste")
                    .build();
                hospitalService.saveMedecin(medecin);
            });

            List<Patient> patients = patientRepository.findAll();
            List<Medecin> medecins = medecinRepository.findAll();

            Patient patient = patients.get(0);
            Medecin medecin = medecins.get(0);

            RendezVous rendezVous = RendezVous.builder()
                .date(new Date())
                .status(StatusRDV.PENDING)
                .patient(patient)
                .medecin(medecin)
                .build();
            RendezVous savedRdv = hospitalService.saveRDV(rendezVous);

            Consultation consultation = Consultation.builder()
                .dateConsultation(new Date())
                .rapport("Rapport de consultation pour " + patient.getNom())
                .rendezVous(savedRdv)
                .build();
            hospitalService.saveConsultation(consultation);

            System.out.println("=== PATIENTS ===");
            patientRepository.findAll().forEach(p ->
                System.out.println(p.getId() + " - " + p.getNom() + " - Malade: " + p.isMalade()));

            System.out.println("\n=== MÉDECINS ===");
            medecinRepository.findAll().forEach(m ->
                System.out.println(m.getId() + " - " + m.getNom() + " - " + m.getSpecialite()));

            System.out.println("\n=== RENDEZ-VOUS ===");
            rendezVousRepository.findAll().forEach(r ->
                System.out.println(r.getId() + " - Patient: " + r.getPatient().getNom()
                    + " - Medecin: " + r.getMedecin().getNom()
                    + " - Status: " + r.getStatus()));
        };
    }
}
