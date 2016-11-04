package thereisnospon.acclient.modules.login;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.Logger;

import thereisnospon.acclient.R;
import thereisnospon.acclient.databinding.RegisterActivityBinding;

public final class RegisterActivity extends AppCompatActivity implements LoginContact.View {

	private RegisterActivityBinding mBinding;
	private LoginContact.Presenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
		mBinding.registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				register();
			}
		});
		mBinding.checkCodeImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.loadCheckCode();
			}
		});
		presenter = new LoginPresenter(this);
	}

	public void register() {

		String id = mBinding.registerId.getText()
		                               .toString();
		String email = mBinding.registerEmail.getText()
		                                     .toString();
		String pass = mBinding.registerPass1.getText()
		                                    .toString();
		String passch = mBinding.registerPass2.getText()
		                                      .toString();
		String check = null;
		if (mBinding.check.getVisibility() == View.VISIBLE) {
			check = mBinding.checkCodeText.getText()
			                              .toString();
		}
		presenter.register(id, email, pass, passch, check);
	}


	@Override
	public void onRegisterSuccess(String userName) {
		Snackbar.make(mBinding.helloLogo, "注册成功:" + userName, Snackbar.LENGTH_SHORT)
		        .setAction("返回登陆", new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
				        finish();
			        }
		        })
		        .show();
	}


	@Override
	public void onRegisterFailure(String error) {
		Logger.d(error);
		mBinding.check.setVisibility(View.VISIBLE);
		presenter.loadCheckCode();
		Snackbar.make(mBinding.helloLogo, error, Snackbar.LENGTH_SHORT)
		        .show();
	}

	@Override
	public void onLoginSuccess(String userName) {
	}

	@Override
	public void onLoginFailure(String error) {
	}


	@Override
	public void onCheckCode(Bitmap bitmap) {
		mBinding.checkCodeImg.setImageBitmap(bitmap);
	}

	@Override
	public void onCheckCodeErr(String error) {
		mBinding.checkCodeImg.setImageBitmap(null);
	}
}
