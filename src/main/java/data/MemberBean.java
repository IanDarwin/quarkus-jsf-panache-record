package data;

import model.Member;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped // Keeps the list of people for the duration of the user's session
public class MemberBean implements Serializable {

	private List<Member> people;
	private Member selectedPerson; // Used for view/edit details
	private boolean editing;

	@PostConstruct
	public void init() {
		loadPeople();
	}

	// Loads all people from the database
	@Transactional // Transaction is required even for read operations in Panache/JPA
	public void loadPeople() {
		// Uses the custom method defined in the Jakarta Data repository
		this.people = Member.listMembers();
	}

	// Navigation and setup methods

	public String viewDetails(Long id) {
		selectedPerson = Member.findById(id);
		editing = false;
		return "/personDetail.xhtml?faces-redirect=true";
	}

	public String editDetails(Long id) {
		selectedPerson = Member.findById(id);
		// Note: For a real app, you'd clone the object to prevent direct database updates
		// until the user hits 'Save'.
		editing = true;
		return "/personDetail.xhtml?faces-redirect=true";
	}

	public String createNew() {
		selectedPerson = new Member();
		editing = true;
		return "/personDetail.xhtml?faces-redirect=true";
	}

	@Transactional
	public String savePerson() {
		if (selectedPerson.getId() == 0) {
			selectedPerson.persist();
		} else {
			Member.getEntityManager().merge(selectedPerson).persistAndFlush();
		}
		loadPeople(); // Refresh the list
		editing = false;
		// Navigate back to the list page
		return "/personList.xhtml?faces-redirect=true";
	}

	// Getters and Setters

	public List<Member> getPeople() {
		return people;
	}

	public Member getSelectedPerson() {
		return selectedPerson;
	}

	public void setSelectedPerson(Member selectedPerson) {
		this.selectedPerson = selectedPerson;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}
}
