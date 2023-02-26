package by.htp.ex.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.htp.ex.controller.CommandName;
import static by.htp.ex.controller.CommandName.*;

public class CommandsFilter implements Filter {
	private static final String ERROR_MESSAGE_PARAM = "error_msg";
	private static final String FIRST_ERROR_MESSAGE_LOCAL_KEY = "no command passed in request";
	private static final String SECOND_ERROR_MESSAGE_LOCAL_KEY = "cannot do this request for this role";
	private static final String THIRD_ERROR_MESSAGE_LOCAL_KEY = "invalid command passed in request";
	
	private static final String COMMAND_PARAM = "command";
	private static final String ROLE_PARAM = "role";
	
	private static final String ROLE_ADMIN = "admin";
	private static final String ROLE_USER = "user";
	private static final String ROLE_GUEST = "guest";
	
	private final static List<CommandName> adminCommands = List.of
            (DO_SIGN_OUT, DO_ADD_NEWS, DO_DELETE_NEWS, DO_EDIT_NEWS, DO_CHANGE_LANGUAGE,
                    GO_TO_ADD_NEWS,GO_TO_EDIT_NEWS, GO_TO_BASE_PAGE, GO_TO_VIEW_NEWS, GO_TO_NEWS_LIST, GO_TO_ERROR_PAGE);
    private final static List<CommandName> userCommands = List.of
            (DO_SIGN_OUT, DO_CHANGE_LANGUAGE, 
            		GO_TO_VIEW_NEWS, GO_TO_NEWS_LIST, GO_TO_BASE_PAGE, GO_TO_ERROR_PAGE);

    private final static List<CommandName> guestCommands = List.of
            (DO_SIGN_IN, DO_REGISTRATION, DO_CHANGE_LANGUAGE, 
            		GO_TO_REGISTRATION_PAGE, GO_TO_VIEW_NEWS, GO_TO_NEWS_LIST, GO_TO_BASE_PAGE, GO_TO_ERROR_PAGE);
    
	private final static Map<String, List<CommandName>> rolePermissions = new HashMap<>() {
		private static final long serialVersionUID = 1L;
	{
		put(ROLE_ADMIN, adminCommands);
		put(ROLE_USER, userCommands);
		put(ROLE_GUEST, guestCommands);
	}};
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         HttpSession session = ((HttpServletRequest) request).getSession(false);
         String commandNameStr = request.getParameter(COMMAND_PARAM);
         
         if (commandNameStr == null) {
             session.setAttribute(ERROR_MESSAGE_PARAM, FIRST_ERROR_MESSAGE_LOCAL_KEY);
         } 
         else {
             try {
                 CommandName commandName = CommandName.valueOf(commandNameStr.toUpperCase());
                 String userRole = (String) session.getAttribute(ROLE_PARAM);
                 
                 if(userRole == null) {
                	 userRole = ROLE_GUEST;
                 }
                 
                 if(!rolePermissions.get(userRole).contains(commandName)) {
                	 session.setAttribute(ERROR_MESSAGE_PARAM, SECOND_ERROR_MESSAGE_LOCAL_KEY); 
                 }
             } catch (IllegalArgumentException e) {
                 session.setAttribute(ERROR_MESSAGE_PARAM, THIRD_ERROR_MESSAGE_LOCAL_KEY);
             }
             
        	 chain.doFilter(request, response);
         }
    }

    @Override
    public void destroy() {}
}