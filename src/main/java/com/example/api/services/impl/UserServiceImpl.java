package com.example.api.services.impl;

import com.example.api.domain.User;
import com.example.api.domain.dto.UserDTO;
import com.example.api.repositories.UserRepository;
import com.example.api.services.UserService;
import com.example.api.services.exceptions.DataIntegralityViolationException;
import com.example.api.services.exceptions.ObjectNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User create(UserDTO obj) {

        findByEmail(obj);

        // convertendo de UserDTO para User, para salvar no banco
        return repository.save(mapper.map(obj, User.class));
    }

    @Override
    public User update(UserDTO obj) {
        // fazendo o tratamento de exceção para email já cadastrado no sistema
        findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    @Override
    public void delete(Integer id) {
        // verificando de existe um id cadastrado
        findById(id);
        repository.deleteById(id);
    }

    public void findByEmail(@NotNull UserDTO obj){
        Optional<User> user = repository.findByEmail(obj.getEmail());
        if( user.isPresent() && !user.get().getId().equals(obj.getId()) ){
            throw new DataIntegralityViolationException("E-mail já cadastrado no sistema");
        }
    }
}
