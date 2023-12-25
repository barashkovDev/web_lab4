package com.barashkov.web_lab4.controllers;

import com.barashkov.web_lab4.models.Appointment;
import com.barashkov.web_lab4.models.Doctor;
import com.barashkov.web_lab4.models.User;
import com.barashkov.web_lab4.repo.AppointmentRepository;
import com.barashkov.web_lab4.repo.DoctorRepository;
import com.barashkov.web_lab4.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public MainController() throws IOException {
    }


    @GetMapping("/")
    public String home(Model model) throws IOException {
        model.addAttribute("title", "Главная страница");
        return "index";
    }

    @GetMapping("/registration")
    public String register(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam String name, @RequestParam String email, @RequestParam String password, Model model) throws UnsupportedEncodingException {
        User user = new User(name, email, password);
        userRepository.save(user);
        model.addAttribute("doctorList", doctorRepository.findAll());
        return "redirect:/name=" + URLEncoder.encode(name, java.nio.charset.StandardCharsets.UTF_8.toString());
    }

    @GetMapping("/authorization")
    public String authorize(Model model) {
        return "authorization";
    }

    @PostMapping("/authorization")
    public String authorizeUser(@RequestParam String name, @RequestParam String password, Model model) throws UnsupportedEncodingException {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("doctorList", doctorRepository.findAll());
        model.addAttribute("name", name);
        for(User user : users) {
            if(user.getName().equals(name) && user.getPassword().equals(password)) {
                return "redirect:/name=" + URLEncoder.encode(name, java.nio.charset.StandardCharsets.UTF_8.toString());
            }
        }
        return "redirect:/";
    }

    @GetMapping("/name={name}")
    public String profile(@PathVariable(value = "name") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("doctorList", doctorRepository.findAll());
        return "accountpage";
    }

    @PostMapping("/name={name}")
    public String appointment(@ModelAttribute(name = "name") String name,
                              @RequestParam String doctorName,
                              @RequestParam String date, Model model) throws UnsupportedEncodingException {
        Long userId = null;
        for(User user : userRepository.findAll()) {
            if(user.getName().equals(name))
                userId = user.getId();
        }
        Long doctorId = null;
        for(Doctor doctor : doctorRepository.findAll()) {
            if(doctor.getName().equals(doctorName))
                doctorId = doctor.getId();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        Appointment appointment = new Appointment(userId, doctorId, dateTime);
        appointmentRepository.save(appointment);
        return "redirect:/name=" + URLEncoder.encode(name, java.nio.charset.StandardCharsets.UTF_8.toString());
    }

    @Scheduled(fixedRate = 59000)
    public void log() throws IOException {
        for(Appointment appointment : appointmentRepository.findAll()) {
            long minutes = ChronoUnit.MINUTES.between(appointment.date, LocalDateTime.now());
            if(minutes == -1440) {
                User user = userRepository.findById(appointment.userId).stream().findAny().orElse(null);
                Doctor doctor = doctorRepository.findById(appointment.doctorId).stream().findAny().orElse(null);
                appendUsingPrintWriter("log.txt", LocalDateTime.now() + "| Привет " +user.getName() + "! Напоминаем что вы записаны к "
                        + doctor.specialization + " завтра в " + appointment.date);
            }
            if(minutes == -120) {
                User user = userRepository.findById(appointment.userId).stream().findAny().orElse(null);
                Doctor doctor = doctorRepository.findById(appointment.doctorId).stream().findAny().orElse(null);
                appendUsingPrintWriter("log.txt", LocalDateTime.now() + "| Привет " +user.getName() + "! Вам через 2 часа к "
                        + doctor.specialization + " в " + appointment.date);
            }
        }
    }

    private static void appendUsingPrintWriter(String filePath, String text) {
        File file = new File(filePath);
        FileWriter fr = null;
        BufferedWriter br = null;
        PrintWriter pr = null;
        try {
            // to append to file, you need to initialize FileWriter using below constructor
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            pr = new PrintWriter(br);
            pr.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pr.close();
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}