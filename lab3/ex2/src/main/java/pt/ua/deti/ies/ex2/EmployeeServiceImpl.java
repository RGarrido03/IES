package pt.ua.deti.ies.ex2;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(@NotNull Employee employee) {
        Optional<Employee> existingOpt = employeeRepository.findById(employee.getId());

        if (existingOpt.isPresent()) {
            Employee existing = existingOpt.get();
            existing.setFirstName(employee.getFirstName());
            existing.setLastName(employee.getLastName());
            existing.setEmail(employee.getEmail());
            return employeeRepository.save(existing);
        } else {
            return null;
        }
    }

    @Override
    public void deleteEmployee(Long userId) {
        employeeRepository.deleteById(userId);
    }
}