package ca.bc.gov.backendstartapi.config;

import com.nimbusds.jose.shaded.json.JSONArray;
import java.security.Principal;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    Principal principal = request.getUserPrincipal();
    try {
      Map<String, Object> map = ((JwtAuthenticationToken) principal).getTokenAttributes();
      String idir_username = "";
      String preferred_username = "";
      String email = "";
      String display_name = "";
      String client_roles = "";
      String identity_provider = "";
      String name = "";
      String family_name = "";
      for (Map.Entry<String, Object> entry : map.entrySet()) {
        //log.info("entry key: {}", entry.getKey());
        switch (entry.getKey()) {
          case "idir_username" -> idir_username = entry.getValue().toString();
          case "preferred_username" -> preferred_username = entry.getValue().toString();
          case "email" -> email = entry.getValue().toString();
          case "display_name" -> display_name = entry.getValue().toString();
          case "identity_provider" -> identity_provider = entry.getValue().toString();
          case "name" -> name = entry.getValue().toString();
          case "family_name" -> family_name = entry.getValue().toString();
          case "client_roles" -> {
            JSONArray jsonArray = (JSONArray) entry.getValue();
            for (int i=0; i<jsonArray.size(); i++) {
              if (!client_roles.isEmpty()) {
                client_roles += ", ";
              }
              client_roles += jsonArray.get(i);
            }
            // log.info("type: {}", entry.getValue().getClass().getName());
          }
        }
      }

      log.info("idir_username: {}", idir_username);
      log.info("preferred_username: {}", preferred_username);
      log.info("email: {}", email);
      log.info("display_name: {}", display_name);
      log.info("client_roles: {}", client_roles);
      log.info("identity_provider: {}", identity_provider);
      log.info("name: {}", name);
      log.info("family_name: {}", family_name);
    } catch (NullPointerException npe) {
      log.info("Not authenticated!");
    }
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}
