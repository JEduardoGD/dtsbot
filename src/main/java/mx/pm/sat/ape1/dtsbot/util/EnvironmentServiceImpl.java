package mx.pm.sat.ape1.dtsbot.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnvironmentServiceImpl implements EnvironmentService {

    @Value("${env.use_fake_ia}")
    private boolean useFakeIa;

    @Override
    public boolean isUseFakeIa() {
            return useFakeIa;
    }
}
