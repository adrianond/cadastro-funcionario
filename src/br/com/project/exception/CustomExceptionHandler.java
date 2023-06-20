package br.com.project.exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;

import br.com.framework.hibernate.session.HibernateUtil;

public class CustomExceptionHandler extends ExceptionHandlerWrapper{
	
	
	private ExceptionHandler wrapperd;
	
	final FacesContext facesContext =  FacesContext.getCurrentInstance();
	
	final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
	
	final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
	
	public CustomExceptionHandler(ExceptionHandler exceptionHandler) {
		   this.wrapperd = exceptionHandler;
	}
	
	// Sobrescreve o metodo ExceptionHandler que retorna a "pilha" de exce��es
	
	@Override
	public ExceptionHandler getWrapped() {
		return wrapperd;
	}
	
	// Sobrescreve o m�todo handle que � respons�vel por manipular as exce��es
	// do JSF
	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
		
		while (iterator.hasNext()){
			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			
			//Recuperar a exce��o do contexto
			Throwable exeption = context.getException();
			
			// Aqui trabalhamos a exce��o
			
			try {
				
				requestMap.put("exceptionMessage", exeption.getMessage());
				
				if (exeption != null &&
						exeption.getMessage() != null
						&& exeption.getMessage().indexOf("ConstraintViolationException") != -1){
					
					FacesContext.getCurrentInstance().
					addMessage("msg",new FacesMessage(FacesMessage.
							SEVERITY_WARN,"Registro n�o pode ser removido por"
									+ " estar associado.",""));
					
				}else if (exeption != null && exeption.getMessage() != null 
						&& exeption.getMessage().
						indexOf("org.hibernate.StaleObjectStateException") != -1) {
					
					FacesContext.getCurrentInstance().addMessage("msg",new 
							FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Registro foi atualizado ou excluido por outro usuario."
					+ " Consulte novamente.",""));
					
				}else {
					// Avisa o usu�rio do erro
					
					// Avisa o usu�rio do erro
					FacesContext.getCurrentInstance().addMessage("msg",new 
							FacesMessage(FacesMessage.SEVERITY_FATAL,"O sistema se recuperou de um erro inesperado.",""));
					
					// Tranquiliza o usu�rio para que ele continue usando o sistema
					FacesContext.getCurrentInstance().addMessage("msg",
							new FacesMessage(FacesMessage.SEVERITY_INFO,"Voce pode continuar usando o sistema normalmente!",""));
					
					FacesContext.getCurrentInstance().addMessage("msg",
							new FacesMessage(FacesMessage.SEVERITY_FATAL,"O erro foi causado por:\n"+exeption.getMessage(), ""));
				
					
					// PrimeFaces
					//esse alert apenas � exibio se a pagina n�o redirecionar
					RequestContext.getCurrentInstance().execute("alert('O sistema se recuperou de um erro inesperado.')");

					
					 RequestContext.getCurrentInstance().
					 showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro", "O sistema se recuperou de um erro inesperado.."));
					
					 // Redireciona para p�gina de erro
					 navigationHandler.handleNavigation(facesContext, null, 
								"/error/error.jsf?faces-redirect=true&expired=true");
					 
				}
				
				// Renderiza a pagina de erro e exibe as mensagens
				facesContext.renderResponse();
			}finally {
				SessionFactory sf = HibernateUtil.getSessionFactory();
				if (sf.getCurrentSession().getTransaction().isActive()){
					sf.getCurrentSession().getTransaction().rollback();
				}
				
				//imprime o erro no console
				exeption.printStackTrace();
				
				iterator.remove();
			}
		}
		
		getWrapped().handle();
		
	}

}
