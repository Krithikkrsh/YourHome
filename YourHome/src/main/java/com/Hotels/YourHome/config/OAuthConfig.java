package com.Hotels.YourHome.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@SuppressWarnings("deprecation")
@EnableAuthorizationServer
public class OAuthConfig extends AuthorizationServerConfigurerAdapter {
    private final String clientId = "Krithik";
    private final String clientSecret = "my-secret-key";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpQIBAAKCAQEAwwFHsxZLrB3xriBzCql/wOPURZo1b8Vp4I/8Q4/aOiiaf9m3\n" +
            "2VMtf78LWMos6+AhudiDqrqOmNBJGtkxtLF+e8YM5vJGMX4b2fasev7VNuIloCXB\n" +
            "BGaNyk4OEf3R65smEUpqIXx+x1A77VKdLnPGJJJ9Nf5Q9IS2nIsWfvSBUS1gs2W9\n" +
            "t58uniTih2dk2KKe8rSPYCQRUVzk80eh55YGr7kGDTZ+1XMSg9lAO+qitxaIct32\n" +
            "v60prv0rSm4BXKSLnml0tPfn51HD7S67+dxZY1ytCLLA+kj/zxz682ouGSe/pJtZ\n" +
            "Cqr98/xWYUIBAfTmJfd+8RNfXQ00ahZBtF0NVwIDAQABAoIBAQClS3B7XWrHepph\n" +
            "/x+E6ihgWL6WQSEKnUwiDerHWiihI8nSlbpBfUAUCNyVZsqtJk9wORdioMA+yE8v\n" +
            "cuuvPsT4JejJ2pOlYEA7gdyyZVBWNVuvZFE4abXGxmMm3QATtm+OOIW93MmaKWii\n" +
            "wmR5+6j5VzLLkccNfXFNQJjRBHCZXWCAsNmJasahFNEYV7tRcNv7IKcSFppLCeuO\n" +
            "bzV6kq4yuAGn5mkCLEm2MNwCK5nlyUW2JM72vWMDA2pTm8g/DuDCEQoKT5TUZj6E\n" +
            "794T6vSYjC1Y0CYxmMmDrtB6GOVgWnpZkGii/C2zD9PGxE1LIEojrzEwxb/4yvJs\n" +
            "3x/cOogBAoGBAPmL2mzT6Gfeb6kxv6MR26SJz3USgL8nGAwHCpre57bykyQDJTC/\n" +
            "fQin7k0kJ83b3jibcaNHLxGj7998qh6YcqPmThx8zzthFsSGa1xn1ZS/okgvLL2V\n" +
            "RGrKGcsHcbVAh1GSHZAjgBONEkdVuuSiXHNqrs7wHKgLg9VKTRhVHv+bAoGBAMgM\n" +
            "VKIFnHQdExNgFazxCzFb7vkJhQiSgfD+shjVB8hXOIRmMkJlfolnvXJEjjRVdEb/\n" +
            "CvmvSvPDy+hOU68HoKcNHMNyu2fvP6EelhZo/UqFpD85F7a0W+d4EiLBmtV8UGFx\n" +
            "/9bSpdMuAQgoUe5LCQOGOiPDqOy1LETpu4k++Sr1AoGBAK5onGRrRocx44CxEGr8\n" +
            "VDKPTrtYDzuKKzhcw4CyQeiPaV9GA3wqmUyvcdHgbyFlhc6ydZ3UZoVBuqjMDGoN\n" +
            "yjHbCMTIRl4N4UfBWs/eklvAA7/HmNF3vbWVQqmvrj0ruGe+Y4MWDLGdayUIOfMJ\n" +
            "tuvDqCFhl+WrBpzPrUpQU7X5AoGAYYNxNdFaZFdP1x/9MOIGvLa9lHOJf5Y69QiS\n" +
            "M/yT4JpzmEZgtXDX7468EBlO4D/PgFQOO4uxE4YbvU+BB07GhuejX7prk8xRze4k\n" +
            "5yOiZFMkrxTbdFoSepBS9tI88Ve5ruZs+YCRCJHiOuG8nlXBPRCPAqP7kfb2b1kn\n" +
            "60zhFIkCgYEA3juVsPTGsVvI63xwceeKFfIlICLR7PY+ffWDxdpnDHXfwCsb7G7C\n" +
            "+nFNo9GhtcsHyZOt0d7acm119N0I7BIq40WRJELo7BVlOvZspMaeExzapU0M5VDQ\n" +
            "iawfy8jDkL02GS8w2nWpH9w6Jt6ECrZX1G+vR1b25iNDti6vlPAuoDQ=\n" +
            "-----END RSA PRIVATE KEY-----";
    private final String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwwFHsxZLrB3xriBzCql/\n" +
            "wOPURZo1b8Vp4I/8Q4/aOiiaf9m32VMtf78LWMos6+AhudiDqrqOmNBJGtkxtLF+\n" +
            "e8YM5vJGMX4b2fasev7VNuIloCXBBGaNyk4OEf3R65smEUpqIXx+x1A77VKdLnPG\n" +
            "JJJ9Nf5Q9IS2nIsWfvSBUS1gs2W9t58uniTih2dk2KKe8rSPYCQRUVzk80eh55YG\n" +
            "r7kGDTZ+1XMSg9lAO+qitxaIct32v60prv0rSm4BXKSLnml0tPfn51HD7S67+dxZ\n" +
            "Y1ytCLLA+kj/zxz682ouGSe/pJtZCqr98/xWYUIBAfTmJfd+8RNfXQ00ahZBtF0N\n" +
            "VwIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public JwtAccessTokenConverter tokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore(){
        return new JwtTokenStore(tokenConverter());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenConverter())
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))
                .scopes("read","write")
                .authorizedGrantTypes("password","refresh_token")
                .authorities("password","refresh_token")
                .accessTokenValiditySeconds(2000)
                .refreshTokenValiditySeconds(20000);
    }
}
