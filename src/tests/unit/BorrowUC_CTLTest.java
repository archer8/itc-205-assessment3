package tests.unit;

/*
    libraries:
 */

import library.BorrowUC_CTL;
import library.BorrowUC_UI;
import library.interfaces.EBorrowState;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/*
    things to be mocked:
 */


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

        reader    = mock(ICardReader.class);
        scanner   = mock(IScanner.class);
        printer   = mock(IPrinter.class);
        display   = mock(IDisplay.class);
        bookDAO   = mock(IBookDAO.class);
        memberDAO = mock(IMemberDAO.class);
        loanDAO   = mock(ILoanDAO.class);
        ui        = mock(BorrowUC_UI.class);
        member    = mock(IMember.class);
        loan      = mock(ILoan.class);

        ctl = new BorrowUC_CTL(reader, scanner, printer, display, bookDAO, loanDAO, memberDAO, ui);


    }

    @After
    public void tearDown() throws Exception {

    }





}