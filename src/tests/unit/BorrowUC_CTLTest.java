package tests.unit;

/*
    libraries:
 */

import javax.swing.*;
import java.util.List;

import library.BorrowUC_UI;
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
import library.interfaces.hardware.ICardReaderListener;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.interfaces.hardware.IScannerListener;


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
        member    = mock(library.interfaces.entities.IMember.class);
        ui = mock(library.BorrowUC_UI.class);


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
    public void doesNotDisplayErrorMessageIfIDIsAccepted() throws Exception {

        when(memberDAO.getMemberByID(1)).thenReturn(member);
        ctl.initialise();
        ctl.cardSwiped(1);
        verify(ui, never()).displayErrorMessage(anyString());

    }

    @Test
    public void displaysErrorWhenCardSwipedHasOverdueFees() throws Exception {
        when(memberDAO.getMemberByID(1)).thenReturn(member);
        when(member.hasReachedLoanLimit()).thenReturn(true);

        ctl.initialise();
        ctl.cardSwiped(1);

        assertEquals(EBorrowState.BORROWING_RESTRICTED, ctl.getState());
        verify(ui).displayAtLoanLimitMessage();


        //verify(ui).displayExistingLoan("");

    }

    @Test
    public void displaysWarningWhenCardSwipedHasOverdueFees() throws Exception {



    }








}