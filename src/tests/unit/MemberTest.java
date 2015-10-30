package tests.unit;

import library.entities.Member;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IMember;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by chris on 10/10/15.
 */
public class MemberTest {
    private Member member;
    private IMember iMember;
    private IMemberDAO memberDAO;


    @Before
    public void setUp() throws Exception {
        memberDAO = mock(library.interfaces.daos.IMemberDAO.class);
        iMember   = mock(library.interfaces.entities.IMember.class);
        member    = mock(library.entities.Member.class);



        //member = new Member("something", "something", "something", "something", 42);


    }


    //no mock, just using the real member to test here.
    @Test
    public void ConstructorThrowsIfIDIsNotGreaterThanZero() throws Exception {
        try {
            Member borrower = new Member("something", "something", "something", "something", -22);
        } catch (Exception e) {
            return;
        }
        fail();
    }

    @Test
    public void CheckIfMemberHasOverdueLoans() throws Exception {

        //when(memberDAO.getMemberByID(42)).thenReturn(member);


        member.addFine(5f);
        assertTrue(member.hasFinesPayable());

    }



}