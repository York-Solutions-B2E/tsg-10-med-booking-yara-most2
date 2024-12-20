import {act, render, waitFor, screen, fireEvent} from "@testing-library/react";
import Appointments from "../Appointments.jsx";
import { SpecializationsProvider } from '../SpecializationsContext';
import React from "react";


jest.mock('../AppointmentsDataGrid.jsx', () => ({ rows, columns, onEdit, onDelete }) => (
    <div data-testid="appointments-grid">
        {rows.map((row) => (
            <div key={row.id}>{row.patient}</div>
        ))}
    </div>
));

jest.mock('../EditModal.jsx', () => ({ show, onHide, onConfirm, appointmentData }) => (
    show ? <div data-testid="edit-modal">Edit Modal</div> : null
));

jest.mock('../ConfirmationModal.jsx', () => ({ show, onHide, onConfirm, body }) => (
    show ? <div data-testid="confirmation-modal">{body}</div> : null
));

describe('Appointments', () => {
    // let setPatientIdMock;
    beforeEach(() => {
        jest.clearAllMocks();
        global.fetch = jest.fn();
        // setPatientIdMock = jest.fn();
        // jest.spyOn(React, 'useState').mockImplementation((init) => {
        //     if (init === null) {
        //         return [null, setPatientIdMock];
        //     }
        //     return [init, jest.fn()];
        // });
    });

    const renderWithProvider = (ui) => {
        return render(<SpecializationsProvider>{ui}</SpecializationsProvider>);
    };

    it('renders the initial state correctly', async () => {
        await act(async () => {
            renderWithProvider(<Appointments />);
        });

        // Wait for any state updates inside SpecializationsProvider to finish
        await waitFor(() => {
            expect(screen.getByText('Please Enter your email to view your appointments')).toBeInTheDocument();
        });
        expect(screen.getByLabelText(/email/i)).toBeInTheDocument();
        expect(screen.getByRole('button', { name: /search/i })).toBeInTheDocument();
    });

    it('should fire the onSearch when Search is clicked', async () => {
        await act(async () => {
            renderWithProvider(<Appointments />);
        });

        const email = 'jd@test.com';

        fireEvent.change(screen.getByLabelText(/email/i), { target: { value: email } });
        fireEvent.click(screen.getByRole('button', { name: /search/i }));

        await waitFor( () => {
            expect(global.fetch).toHaveBeenCalledWith('http://localhost:8080/api/patients/e?email=jd@test.com');
        })

    });

    it('fetches patient data and appointments on search button', async () => {
        global.fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => ({
                id: 1,
                firstName: 'John',
                lastName: 'Doe',
                email: 'jd@test.com',
                dateOfBirth: '1999-10-10',
            }),
        });
        global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => [
            {
                id: 1,
                patient: {
                    id: 1,
                    firstName: 'John',
                    lastName: 'Doe',
                    email: 'jd@test.com',
                    dateOfBirth: '1999-10-10',
                },
                doctor: {
                    id: 1,
                    firstName: 'Dr. Smith',
                    lastName: 'Brown',
                    specialization: {
                        id: 1,
                        name: 'Cardiology' ,
                    }
                },
                appointmentDateTime: '2023-12-01T10:00:00',
                visitType: 'TELEHEALTH',
                confirmed: true,
            },
        ],
    });


        await act(async () => {
            renderWithProvider(<Appointments />);
        });

        // Input email and click search
        await act(async () => {
            fireEvent.change(screen.getByLabelText(/email/i), { target: { value: 'jd@test.com' } });
            fireEvent.click(screen.getByRole('button', { name: /search/i }));
        });


        // Wait for state updates
        await waitFor(() => {
            // expect(setPatientIdMock).toHaveBeenCalledWith(1);
            expect(screen.getByTestId('appointments-grid')).toBeInTheDocument();
            // expect(screen.getByText('John Doe')).toBeInTheDocument();
            // expect(screen.getByText(/Cardiology/i)).toBeInTheDocument();
        });
    });
});


// global.fetch.mockResolvedValueOnce({
//     ok: true,
//     json: async () => [
//         {
//             id: 1,
//             patient: {
//                 id: 1,
//                 firstName: 'John',
//                 lastName: 'Doe',
//                 email: 'jd@test.com',
//                 dateOfBirth: '1999-10-10',
//             },
//             doctor: {
//                 id: 1,
//                 firstName: 'Dr. Smith',
//                 lastName: 'Brown',
//                 specialization: {
//                     id: 1,
//                     name: 'Cardiology' ,
//                 }
//             },
//             appointmentDateTime: '2023-12-01T10:00:00',
//             visitType: 'TELEHEALTH',
//             confirmed: true,
//         },
//     ],
// });