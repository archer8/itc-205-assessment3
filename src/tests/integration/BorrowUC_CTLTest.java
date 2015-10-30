package tests.integration;

/*
    libraries:
 */

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import library.BorrowUC_UI;
import library.entities.Member;
import library.panels.borrow.ABorrowPanel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



/*
    things to be mocked:
 */
import library.BorrowUC_CTL;
import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;



/**
 * Created by chris on 8/10/15.
 */
public class BorrowUC_CTLTest {
    private ICardReader reader;
    private IScanner scanner;
    private IPrinter printer;
    private IDisplay display;
    private int scanCount = 0;
    private BorrowUC_UI ui;
    private EBorrowState state;
    private IBookDAO bookDAO;
    private IMemberDAO memberDAO;
    private ILoanDAO loanDAO;
    private List<IBook> bookList;
    private List<ILoan> loanList;
    private IMember member;
    private ILoan loan;
    //private JPanel previous;

    private BorrowUC_CTL ctl;


    @Before
    public void setUp() throws Exception {

        reader    = mock(library.interfaces.hardware.ICardReader.class);
        scanner   = mock(library.interfaces.hardware.IScanner.class);
        printer   = mock(library.interfaces.hardware.IPrinter.class);
        display   = mock(library.interfaces.hardware.IDisplay.class);
        bookDAO   = mock(library.interfaces.daos.IBookDAO.class);
        memberDAO = mock(library.interfaces.daos.IMemberDAO.class);
        loanDAO   = mock(library.interfaces.daos.ILoanDAO.class);
        ui        = mock(library.BorrowUC_UI.class);
        member    = mock(library.interfaces.entities.IMember.class);
        loan      = mock(library.interfaces.entities.ILoan.class);

        ctl = new BorrowUC_CTL(reader, scanner, printer, display, bookDAO, loanDAO, memberDAO, ui);


    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void displaysErrorMessageIfMemberIDNotFound() throws Exception {
        ctl.initialise();
        ctl.cardSwiped(100);
        verify(ui).displayErrorMessage("Member ID 100 not found");
    }


    // This test checks the card swiped is a valid ID number.
    @Test
    public void noErrorMessageIfIDIsAccepted() throws Exception {

        when(memberDAO.getMemberByID(1)).thenReturn(member);
        ctl.initialise();
        ctl.cardSwiped(1);
        verify(ui, never()).displayErrorMessage(anyString());

    }

    // no mock member provided, so this should successfully show an error:
    @Test
    public void ErrorMessageIfIDNotFound() throws Exception {
        ctl.initialise();
        ctl.cardSwiped(1);
        verify(ui).displayErrorMessage("Member ID 1 not found");
    }

    // test if state is successfully changed to SCANNING_BOOKS
    @Test
    public void scanningBooksStateEnabledWhenCardSwipedWithNoProblems() throws Exception {
        when(memberDAO.getMemberByID(anyInt())).thenReturn(member);
        ctl.initialise();
        ctl.cardSwiped(1);
        assertEquals(EBorrowState.SCANNING_BOOKS, ctl.getState());
    }


    @Test
    public void borrowingRestrictedWhenCardSwipedHasReachedLoanLimit() throws Exception {
        when(memberDAO.getMemberByID(42)).thenReturn(member);
        when(member.hasReachedLoanLimit()).thenReturn(true);

        ctl.initialise();
        ctl.cardSwiped(42);

        verify(ui).displayAtLoanLimitMessage();
        verify(scanner, atLeastOnce()).setEnabled(false);
        assertEquals(EBorrowState.BORROWING_RESTRICTED, ctl.getState());


    }

    @Test
    public void borrowingRestrictedWhenCardSwipedHasExceededFineLimit() throws Exception {
        when(memberDAO.getMemberByID(42)).thenReturn(member);
        when(member.hasReachedFineLimit()).thenReturn(true);

        ctl.initialise();
        ctl.cardSwiped(42);

        assertEquals(EBorrowState.BORROWING_RESTRICTED, ctl.getState());

    }

    @Test
    public void displaysBorrowerDetailsAfterCardSwiped() throws Exception {
        when(memberDAO.getMemberByID(42)).thenReturn(member);
        when(member.getID()).thenReturn(42);
        when(member.getFirstName()).thenReturn("fname");
        when(member.getLastName()).thenReturn("lname");
        when(member.getContactPhone()).thenReturn("0001");

        ctl.initialise();
        ctl.cardSwiped(42);

        verify(ui).displayMemberDetails(42, "fname lname", "0001");
    }


    @Test
    public void displaysAnErrorMessageIfRestrictionsAfterCardSwiped() throws Exception {
        when(memberDAO.getMemberByID(42)).thenReturn(member);
        when(member.getID()).thenReturn(42);
        when(member.hasFinesPayable()).thenReturn(true);
        when(member.getFineAmount()).thenReturn(15.0f);

        ctl.initialise();
        ctl.cardSwiped(42);

        verify(ui).displayOutstandingFineMessage(15.0f);


    }
    @Test
    public void displaysExistingLoansAfterCardSwiped() throws Exception {

        List<ILoan> list = new ArrayList<ILoan>();
        list.add(loan);

        when(memberDAO.getMemberByID(42)).thenReturn(member);
        when(member.getID()).thenReturn(42);
        when(member.getLoans()).thenReturn(list);
        when(loan.toString()).thenReturn("loan info");

        ctl.initialise();
        ctl.cardSwiped(42);

        verify(ui).displayExistingLoan("loan info");

    }



}