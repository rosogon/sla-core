package eu.atos.sla.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import eu.atos.sla.dao.IVariableDAO;
import eu.atos.sla.datamodel.IVariable;
import eu.atos.sla.datamodel.bean.Variable;

@Repository("VariableRepository")
public class VariableDAOJpa implements IVariableDAO {
	private static Logger logger = Logger.getLogger(VariableDAOJpa.class);
	private EntityManager entityManager;

	@PersistenceContext(unitName = "slarepositoryDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Variable getById(Long id) {
		return entityManager.find(Variable.class, id);
	}

	
	@Override
	public IVariable getByName(String name) {
		try {
			TypedQuery<IVariable> query = entityManager.createNamedQuery(
					Variable.QUERY_FIND_BY_NAME, IVariable.class);
			query.setParameter("name", name);
			IVariable variable = new Variable();

			variable = query.getSingleResult();
			

			return variable;

		} catch (NoResultException e) {
			logger.debug("No Result found: " + e);
			return null;
		}
	}

	@Override
	public List<IVariable> getAll() {

		TypedQuery<IVariable> query = entityManager.createNamedQuery(
				Variable.QUERY_FIND_ALL, IVariable.class);
		List<IVariable> services = new ArrayList<IVariable>();
		services = query.getResultList();

		if (services != null) {
			logger.debug("Number of services:" + services.size());
		} else {
			logger.debug("No Result found.");
		}

		return services;
	}

	@Override
	public IVariable save(IVariable variable){
		
			entityManager.persist(variable);
			entityManager.flush();
		
		return variable;
	}

	@Override
	public boolean update(IVariable variable) {
		entityManager.merge(variable);
		entityManager.flush();
		return true;
	}

	@Override
	public boolean delete(IVariable variable) {
		try {
			variable = entityManager
					.getReference(Variable.class, variable.getId());
			entityManager.remove(variable);
			entityManager.flush();
			return true;
		} catch (EntityNotFoundException e) {
			logger.debug(e);
			return false;
		}
	}
}
