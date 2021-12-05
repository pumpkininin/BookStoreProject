package sad.fit2021.bookstoreproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sad.fit2021.bookstoreproject.model.entity.User;
import sad.fit2021.bookstoreproject.model.entity.VerificationToken;
import sad.fit2021.bookstoreproject.repository.TokenRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

@Service
public class VerificationTokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    public VerificationToken getToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);

    }

    @Transactional
    public VerificationToken getTokenByUser(User user) {
        return tokenRepository.findByUser(user);
    }


    public void saveToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        //save expiry token valid in 2 days
        verificationToken.setExpiryDate(calculateExpiryDate(24 * 60 * 2));
        tokenRepository.save(verificationToken);
    }
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public VerificationToken generateNewToken(String existingToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingToken);
        updateToken(vToken);
        vToken = tokenRepository.save(vToken);
        return vToken;
    }
    //calculate expiration time
    private Timestamp calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }

    public void updateToken(VerificationToken tokenObj) {
        tokenObj.setToken(UUID.randomUUID().toString());
        tokenObj.setExpiryDate(calculateExpiryDate(24 * 60 * 2));
    }
}
