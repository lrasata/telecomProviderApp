import React from 'react';
import { Translate, ValidatedField, translate } from 'react-jhipster';
import { Form } from 'reactstrap';
import { Link } from 'react-router-dom';
import { type FieldError, useForm } from 'react-hook-form';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { login } from 'app/shared/reducers/authentication';
import { Typography } from '@mui/material';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Alert from '@mui/material/Alert';
import Button from '@mui/material/Button';
import { BrandIcon } from 'app/shared/layout/header/header-components';
import Box from '@mui/material/Box';

const LoginWithoutModal = () => {
  const dispatch = useAppDispatch();
  const loginError = useAppSelector(state => state.authentication.loginError);

  const handleLogin = (username, password, rememberMe = false) => dispatch(login(username, password, rememberMe));

  const onLogin = ({ username, password, rememberMe }) => {
    handleLogin(username, password, rememberMe);
  };

  const {
    handleSubmit,
    register,
    formState: { errors, touchedFields },
  } = useForm({ mode: 'onTouched' });

  const handleLoginSubmit = e => {
    handleSubmit(onLogin)(e);
  };

  return (
    <Form onSubmit={handleLoginSubmit}>
      <Card>
        <CardContent>
          <Box display="flex" flexDirection="column" alignItems="center" sx={{ py: 3 }}>
            <BrandIcon />
            <Typography variant="h5" sx={{ my: 2 }}>
              <Translate contentKey="global.title">TelecomProviderApp</Translate>
            </Typography>
          </Box>
          <Typography variant="h6" gutterBottom>
            <Translate contentKey="login.title">Sign in</Translate>
          </Typography>
          {loginError ? (
            <Alert severity="error" data-cy="loginError">
              <Translate contentKey="login.messages.error.authentication">
                <strong>Failed to sign in!</strong> Please check your credentials and try again.
              </Translate>
            </Alert>
          ) : null}
          <ValidatedField
            name="username"
            label={translate('global.form.username.label')}
            placeholder={translate('global.form.username.placeholder')}
            required
            autoFocus
            data-cy="username"
            validate={{ required: 'Username cannot be empty!' }}
            register={register}
            error={errors.username as FieldError}
            isTouched={touchedFields.username}
          />
          <ValidatedField
            name="password"
            type="password"
            label={translate('login.form.password')}
            placeholder={translate('login.form.password.placeholder')}
            required
            data-cy="password"
            validate={{ required: 'Password cannot be empty!' }}
            register={register}
            error={errors.password as FieldError}
            isTouched={touchedFields.password}
          />
          <ValidatedField
            name="rememberMe"
            type="checkbox"
            check
            label={translate('login.form.rememberme')}
            value={true}
            register={register}
          />
          <Alert severity="warning" sx={{ my: 1 }}>
            <Link to="/account/reset/request" data-cy="forgetYourPasswordSelector">
              <Translate contentKey="login.password.forgot">Did you forget your password?</Translate>
            </Link>
          </Alert>
          <Alert severity="warning" sx={{ mb: 1 }}>
            <span>
              <Translate contentKey="global.messages.info.register.noaccount">You don&apos;t have an account yet?</Translate>
            </span>{' '}
            <Link to="/account/register">
              <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
            </Link>
          </Alert>
          <Button variant="contained" type="submit" data-cy="submit">
            <Translate contentKey="login.form.button">Sign in</Translate>
          </Button>
        </CardContent>
      </Card>
    </Form>
  );
};

export default LoginWithoutModal;
