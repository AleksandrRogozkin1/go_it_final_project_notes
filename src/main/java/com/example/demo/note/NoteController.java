package com.example.demo.note;

import com.example.demo.exception.note.NoteNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {
    private final NoteValidator noteValidator;
    private final NoteService noteService;

    @GetMapping("/list")
    public ModelAndView listNotes() {
        return new ModelAndView("note/list").addObject("allNotes", noteService.getAllNotes());
    }

    @GetMapping("/create")
    public ModelAndView getAddForm() {
        return new ModelAndView("note/create");
    }

    @PostMapping("/create")
    public ModelAndView createNote(@ModelAttribute @Valid NoteDto note, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        noteValidator.validate(note, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("error/base-error");
        } else {
            noteService.createNote(note);
            modelAndView.setViewName("redirect:/note/list");
        }

        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView showEditNoteForm(@RequestParam UUID id) throws NoteNotFoundException {
        return new ModelAndView("note/editor").addObject("editNote", noteService.getById(id));
    }

    @PostMapping("/edit")
    public ModelAndView saveNoteChanges(@ModelAttribute NoteDto note) throws NoteNotFoundException {
        noteService.updateNote(note);
        return new ModelAndView("redirect:/note/list");
    } // need to secure

    @PostMapping("/delete")
    public ModelAndView deleteNote(@RequestParam UUID id) throws NoteNotFoundException {
        noteService.deleteById(id);
        return new ModelAndView("redirect:/note/list");
    } // need to secure

    @GetMapping("/share")
    public ModelAndView showShareNoteForm(@RequestParam UUID id) {
        return new ModelAndView(/* test */);
    } // need to secure
}