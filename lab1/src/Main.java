import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Patient {
    private int id;
    private String lastName;
    private String firstName;
    private String surname;
    private String address;
    private String phone;
    private String medicalCardNumber;
    private String diagnosis;

    public Patient() {
    }

    public Patient(int id, String lastName, String firstName, String surname, String address, String phone, String medicalCardNumber, String diagnosis) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.medicalCardNumber = medicalCardNumber;
        this.diagnosis = diagnosis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return surname;
    }

    public void setSurName(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMedicalCardNumber() {
        return medicalCardNumber;
    }

    public void setMedicalCardNumber(String medicalCardNumber) {
        this.medicalCardNumber = medicalCardNumber;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", medicalCardNumber='" + medicalCardNumber + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, surname, address, phone, medicalCardNumber, diagnosis);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id &&
                Objects.equals(lastName, patient.lastName) &&
                Objects.equals(firstName, patient.firstName) &&
                Objects.equals(surname, patient.surname) &&
                Objects.equals(address, patient.address) &&
                Objects.equals(phone, patient.phone) &&
                Objects.equals(medicalCardNumber, patient.medicalCardNumber) &&
                Objects.equals(diagnosis, patient.diagnosis);
    }
}

class PatientManager {
    private final List<Patient> patients;

    public PatientManager() {
        this.patients = new ArrayList<>();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public List<Patient> getPatientsByDiagnosis(String diagnosis) {
        List<Patient> result = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getDiagnosis().equalsIgnoreCase(diagnosis)) {
                result.add(patient);
            }
        }
        return result;
    }

    public List<Patient> getPatientsByMedicalCardRange(String start, String end) {
        List<Patient> result = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getMedicalCardNumber().compareTo(start) >= 0 &&
                patient.getMedicalCardNumber().compareTo(end) <= 0) {
                result.add(patient);
            }
        }
        return result;
    }

    public void printPatients(List<Patient> patients) {
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        PatientManager patientManager = new PatientManager();

        patientManager.addPatient(new Patient(1, "Иванов", "Иван", "Иванович", "ул. Ленина, 10", "1234567890", "000001", "Грипп"));

        patientManager.addPatient(new Patient(2, "Петров", "Петр", "Петрович", "ул. Московская, 20", "0987654321", "000002", "ОРВИ"));

        patientManager.addPatient(new Patient(3, "Сидоров", "Алексей", "Алексеевич", "ул. Советская, 30", "1122334455", "000003", "Грипп"));

        System.out.println("Пациенты с диагнозом 'ОРВИ':");
        patientManager.printPatients(patientManager.getPatientsByDiagnosis("ОРВИ"));

        System.out.println("\nПациенты с номерами медицинских карт в интервале '000001' - '000003':");
        patientManager.printPatients(patientManager.getPatientsByMedicalCardRange("000001", "000003"));
    }
}