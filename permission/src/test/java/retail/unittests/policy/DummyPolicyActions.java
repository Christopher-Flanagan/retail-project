package retail.unittests.policy;

import retail.policy.PolicyActions;

public enum DummyPolicyActions implements PolicyActions {
    DUMMY_ISSUE,
    DUMMY_REQUEST,
    DUMMY_READ,
    DUMMY_WRITE
}
