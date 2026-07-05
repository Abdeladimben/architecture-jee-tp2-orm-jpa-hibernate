package ma.bdcc.service;

import ma.bdcc.entities.*;
import java.util.List;

public interface IHospitalService {
    Patient savePatient(Patient patient);
    Medecin saveMedecin(Medecin medecin);
    RendezVous saveRDV(RendezVous rendezVous);
    Consultation saveConsultation(Consultation consultation);
    List<Patient> getPatients();
    List<Medecin> getMedecins();
    Patient findPatientById(Long id);
    Medecin findMedecinById(Long id);
}
