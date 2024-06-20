package org.example.eksamenbackend.result;

import org.example.eksamenbackend.errorhandling.exception.NotFoundException;
import org.example.eksamenbackend.errorhandling.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultService {
    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public List<ResultDTO> findAll() {
        return resultRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ResultDTO createResult(ResultDTO resultDTO) {
        if (resultDTO.getResultType() == null || resultDTO.getResultType().isEmpty() || resultDTO.getDate() == null) {
            throw new ValidationException("Result and date must be provided");
        }
        Result result = new Result();
        result.setResultType(resultDTO.getResultType());
        result.setDate(resultDTO.getDate());
        result.setResultValue(resultDTO.getResultValue());
        resultRepository.save(result);
        return toDTO(result);
    }

    private ResultDTO toDTO(Result result) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setId(result.getId());
        resultDTO.setResultType(result.getResultType());
        resultDTO.setDate(result.getDate());
        resultDTO.setResultValue(result.getResultValue());
        return resultDTO;
    }

    public ResultDTO deleteResult(Long id) {
        Result result = resultRepository.findById(id).orElseThrow(() -> new NotFoundException("Result not found"));
        resultRepository.deleteById(id);
        return toDTO(result);
    }

    public ResultDTO updateResult(Long id, ResultDTO resultDTO) {
        Result result = resultRepository.findById(id).orElseThrow(() -> new NotFoundException("Result not found"));
        if (resultDTO.getResultType() == null || resultDTO.getResultType().isEmpty() || resultDTO.getDate() == null) {
            throw new ValidationException("Result type and date must be provided");
        }
        result.setResultType(resultDTO.getResultType());
        result.setDate(resultDTO.getDate());
        result.setResultValue(resultDTO.getResultValue());
        resultRepository.save(result);
        return toDTO(result);
    }

    public Optional<ResultDTO> findById(Long id) {
        if (id == null || id < 0) {
            throw new ValidationException("ID must be provided");
        }
        return resultRepository.findById(id).map(this::toDTO);
    }
}
