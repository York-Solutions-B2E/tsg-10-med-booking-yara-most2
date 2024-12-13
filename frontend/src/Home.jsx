import {Container, Typography} from "@mui/material";

function Home() {
    return (
        <Container
            sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                height: '100vh',
                textAlign: 'center',
            }}
        >
            <Typography variant="h2" gutterBottom>
                Welcome to the Health Portal
            </Typography>
            <Typography variant="h6" color="textSecondary" gutterBottom>
                Manage appointments, doctors, and patients effortlessly!
            </Typography>

        </Container>
    );
}

export default Home;